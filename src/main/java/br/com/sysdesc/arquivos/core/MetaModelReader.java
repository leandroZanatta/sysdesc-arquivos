package br.com.sysdesc.arquivos.core;

import static br.com.sysdesc.arquivos.util.WrapperThrowable.wrap;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import br.com.sysdesc.arquivos.exceptions.FileMapperException;
import br.com.sysdesc.arquivos.formatters.enumerators.FormatterEnum;
import br.com.sysdesc.arquivos.metamodel.Formatter;
import br.com.sysdesc.arquivos.metamodel.GroupOfRecords;
import br.com.sysdesc.arquivos.metamodel.IdField;
import br.com.sysdesc.arquivos.metamodel.IgnoreField;
import br.com.sysdesc.arquivos.metamodel.IgnoreFields;
import br.com.sysdesc.arquivos.metamodel.IgnoreId;
import br.com.sysdesc.arquivos.metamodel.Record;
import br.com.sysdesc.arquivos.metamodel.RootFile;
import br.com.sysdesc.arquivos.model.FieldModel;
import br.com.sysdesc.arquivos.model.FormatterModel;
import br.com.sysdesc.arquivos.model.GroupRecordsModel;
import br.com.sysdesc.arquivos.model.IdFieldModel;
import br.com.sysdesc.arquivos.model.InnerRecordModel;
import br.com.sysdesc.arquivos.model.RecordModel;
import br.com.sysdesc.arquivos.model.TexFileModel;
import br.com.sysdesc.arquivos.util.ReflectionUtils;

public class MetaModelReader {

	private MetaModelReader() {
	}

	public static <T> TexFileModel readMetaModel(Class<T> instanceObject) throws FileMapperException {

		if (!instanceObject.isAnnotationPresent(RootFile.class)) {

			throw new FileMapperException("A classe informada não possui anotação de RootFile, Verifique");
		}

		TexFileModel model = new TexFileModel();

		model.setGroupRecords(readGroups(instanceObject));

		return model;
	}

	private static <K> GroupRecordsModel readGroups(Class<K> instanceObject) throws FileMapperException {

		if (!instanceObject.isAnnotationPresent(GroupOfRecords.class)) {

			throw new FileMapperException(String.format("A classe %s não possui anotação de GroupOfRecords, Verifique",
					instanceObject.getName()));
		}

		GroupRecordsModel modeloGrupo = new GroupRecordsModel();

		List<Field> recordList = Arrays.asList(instanceObject.getDeclaredFields()).stream()
				.filter(field -> field.isAnnotationPresent(Record.class))
				.collect(Collectors.toList());

		for (Field field : recordList) {

			modeloGrupo.getRecords().add(readRecords(field));
		}

		Collections.sort(modeloGrupo.getRecords(), Comparator.comparing(RecordModel::getOrder));

		return modeloGrupo;
	}

	private static RecordModel readRecords(Field field) throws FileMapperException {

		RecordModel recordModel = new RecordModel();
		recordModel.setRepetable(Collection.class.isAssignableFrom(field.getType()));
		recordModel.setName(field.getName());
		recordModel.setOrder(field.getAnnotation(Record.class).order());
		recordModel.setInnerRecordModel(readInnerRecords(field, recordModel.isRepetable()));

		return recordModel;
	}

	private static InnerRecordModel readInnerRecords(Field field, boolean repetable) throws FileMapperException {

		if (repetable) {

			return readRepetableRecords(ReflectionUtils.getGenericTypeFromList(field.getGenericType()));
		}

		return readRecord(field.getType());
	}

	private static InnerRecordModel readRecord(Class<?> clazz) throws FileMapperException {
		InnerRecordModel innerRecordModel = new InnerRecordModel();
		innerRecordModel.setIdField(readIdFieldFromclass(clazz));

		return innerRecordModel;
	}

	private static InnerRecordModel readRepetableRecords(Class<?> clazz) throws FileMapperException {
		InnerRecordModel innerRecordModel = new InnerRecordModel();
		innerRecordModel.setInnerRecords(readGroups(clazz));

		innerRecordModel.setIdField(innerRecordModel.getInnerRecords().getRecords().get(0).getInnerRecordModel().getIdField());

		return innerRecordModel;
	}

	private static IdFieldModel readIdFieldFromclass(Class<?> clazz) throws FileMapperException {
		List<Field> fieldsFromClass = Arrays.asList(clazz.getDeclaredFields());

		List<FieldModel> fieldsSemId = fieldsFromClass.stream()
				.filter(fieldIterado -> !fieldIterado.isAnnotationPresent(IdField.class))
				.map(wrap(MetaModelReader::mapFieldToModel)).collect(Collectors.toList());

		if (clazz.isAnnotationPresent(IgnoreFields.class)) {

			fieldsSemId.addAll(Arrays.asList(clazz.getAnnotation(IgnoreFields.class).fields()).stream()
					.map(MetaModelReader::mapMetaModelIgnoreField).collect(Collectors.toList()));
		}

		IdFieldModel idFieldModel = getIdField(clazz, fieldsFromClass);
		idFieldModel.setFields(fieldsSemId);

		return idFieldModel;
	}

	private static IdFieldModel getIdField(Class<?> clazz, List<Field> fieldsFromClass) throws FileMapperException {

		if (clazz.isAnnotationPresent(IgnoreId.class)) {

			return mapMetaModelIgnoreIdField(clazz.getAnnotation(IgnoreId.class));
		}

		Field field = fieldsFromClass.stream().filter(fieldIterado -> fieldIterado.isAnnotationPresent(IdField.class))
				.findFirst().orElseThrow(() -> new FileMapperException(
						String.format("A classe %s não possui anotação de IdField, Verifique", clazz.getName())));

		return mapIdFieldToModel(field);
	}

	private static IdFieldModel mapMetaModelIgnoreIdField(IgnoreId metadata) {
		IdFieldModel idFieldModel = new IdFieldModel();

		idFieldModel.setClazz(metadata.type());
		idFieldModel.setName(metadata.name());
		idFieldModel.setOrder(metadata.order());
		idFieldModel.setStart(metadata.start());
		idFieldModel.setEnd(metadata.end());
		idFieldModel.setValue(metadata.defaultValue());
		idFieldModel.setFormatter(convertFormatter(metadata.formatter()));

		return idFieldModel;
	}

	private static IdFieldModel mapIdFieldToModel(Field field) throws FileMapperException {

		if (field.isAnnotationPresent(br.com.sysdesc.arquivos.metamodel.IdField.class)) {
			br.com.sysdesc.arquivos.metamodel.IdField metaIdField = field
					.getAnnotation(br.com.sysdesc.arquivos.metamodel.IdField.class);

			IdFieldModel idFieldModel = new IdFieldModel();
			idFieldModel.setClazz(field.getType());
			idFieldModel.setName(field.getName());
			idFieldModel.setOrder(metaIdField.order());
			idFieldModel.setStart(metaIdField.start());
			idFieldModel.setEnd(metaIdField.end());
			idFieldModel.setValue(metaIdField.defaultValue());

			addFormatters(idFieldModel, field);

			return idFieldModel;
		}

		throw new FileMapperException(
				String.format("O campo %s não possui anotação de IdField, Verifique", field.getName()));
	}

	private static FieldModel mapFieldToModel(Field field) throws FileMapperException {

		if (field.isAnnotationPresent(br.com.sysdesc.arquivos.metamodel.Field.class)) {
			br.com.sysdesc.arquivos.metamodel.Field metaField = field
					.getAnnotation(br.com.sysdesc.arquivos.metamodel.Field.class);

			FieldModel fieldModel = new FieldModel();
			fieldModel.setClazz(field.getType());
			fieldModel.setName(field.getName());
			fieldModel.setOrder(metaField.order());
			fieldModel.setStart(metaField.start());
			fieldModel.setEnd(metaField.end());
			fieldModel.setValue(metaField.defaultValue());

			addFormatters(fieldModel, field);

			return fieldModel;
		}

		throw new FileMapperException(
				String.format("O campo %s não possui anotação de Field, Verifique", field.getName()));
	}

	private static FieldModel mapMetaModelIgnoreField(IgnoreField ignoreField) {

		FieldModel fieldModel = new FieldModel();
		fieldModel.setClazz(ignoreField.type());
		fieldModel.setName(ignoreField.name());
		fieldModel.setOrder(ignoreField.order());
		fieldModel.setStart(ignoreField.start());
		fieldModel.setEnd(ignoreField.end());
		fieldModel.setValue(ignoreField.defaultValue());
		fieldModel.setFormatter(convertFormatter(ignoreField.formatter()));

		return fieldModel;
	}

	private static void addFormatters(FieldModel fieldModel, Field field) {

		if (field.isAnnotationPresent(Formatter.class)) {

			fieldModel.setFormatter(convertFormatter(field.getAnnotation(Formatter.class)));
		}

		if (fieldModel.getFormatter() == null) {

			if (Number.class.isAssignableFrom(fieldModel.getClazz())) {

				fieldModel.setFormatter(new FormatterModel(FormatterEnum.PADDING_LEFT, "0"));
			} else {

				fieldModel.setFormatter(new FormatterModel(FormatterEnum.PADDING_RIGHT, " "));
			}
		}
	}

	private static FormatterModel convertFormatter(Formatter formatter) {

		return new FormatterModel(formatter.type(), formatter.value());
	}
}
