package br.com.sysdesc.arquivos.core;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import br.com.sysdesc.arquivos.exceptions.FileMapperException;
import br.com.sysdesc.arquivos.exceptions.FormatNotSuportedException;
import br.com.sysdesc.arquivos.model.FieldModel;
import br.com.sysdesc.arquivos.model.IdFieldModel;
import br.com.sysdesc.arquivos.model.InnerRecordModel;
import br.com.sysdesc.arquivos.model.RecordModel;
import br.com.sysdesc.arquivos.model.TexFileModel;
import br.com.sysdesc.arquivos.util.ListUtil;
import br.com.sysdesc.arquivos.util.RecordIterator;
import br.com.sysdesc.arquivos.util.ReflectionUtils;

public class FileObjectReader {

	private FileObjectReader() {
	}

	public static <T> T getObjectFromTextFile(Class<T> classFile, List<String> textFile, TexFileModel textFileModel)
			throws InstantiationException, IllegalAccessException, FileMapperException, FormatNotSuportedException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {

		return mapObject(classFile.getConstructor().newInstance(), textFile, textFileModel.getGroupRecords().getRecords(), new RecordIterator());
	}

	private static <K> K mapObject(K object, List<String> textFile, List<RecordModel> records, RecordIterator recordIterator)
			throws FileMapperException, InstantiationException, IllegalAccessException, FormatNotSuportedException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {

		for (RecordModel record : records) {

			java.lang.reflect.Field field = getFieldFromMapper(object, record);

			mapearObjeto(object, textFile, field, record, recordIterator);
		}

		return object;
	}

	private static <K> Field getFieldFromMapper(K object, RecordModel record) throws FileMapperException {

		return Arrays.asList(object.getClass().getDeclaredFields()).stream()
				.filter(field -> field.getName().equals(record.getName())).findFirst()
				.orElseThrow(() -> new FileMapperException(String.format("O Objeto %s não está mapeado na classe %s",
						record.getName(), object.getClass().getName())));
	}

	private static <K> void mapearObjeto(K object, List<String> textFile, Field field, RecordModel record, RecordIterator recordIterator)
			throws InstantiationException, IllegalAccessException, FileMapperException, FormatNotSuportedException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {

		if (record.isRepetable()) {

			if (!Collection.class.isAssignableFrom(field.getType())) {

				throw new FileMapperException(String.format(
						"O Atributo %s está mapeado como Objeto na classe %s, porém possui uma coleção de atributos",
						record.getName(), object.getClass().getName()));
			}

			Collection<?> collection = mapearColecao(field, textFile, record, recordIterator);

			ReflectionUtils.setValue(object, field, collection);

		} else {

			if (record.getInnerRecordModel() == null) {

				throw new FileMapperException(
						String.format("O Atributo %s está mapeado como Objeto na classe %s, porém não possui valor",
								record.getName(), object.getClass().getName()));
			}

			Object objeto = field.getType().getConstructor().newInstance();

			getValueObject(objeto, textFile, record.getInnerRecordModel(), recordIterator);

			ReflectionUtils.setValue(object, field, objeto);
		}

	}

	@SuppressWarnings("unchecked")
	private static <M> Collection<M> mapearColecao(Field field, List<String> textFile, RecordModel record, RecordIterator recordIterator)
			throws InstantiationException, IllegalAccessException, FileMapperException, FormatNotSuportedException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {

		Collection<M> colecao = instanciarColecao(field);

		while (textFile.size() > recordIterator.getRow() && record.getInnerRecordModel().isIdField(textFile.get(recordIterator.getRow()))) {

			M objetoLista = (M) ReflectionUtils.getGenericTypeFromList(field.getGenericType()).getConstructor().newInstance();

			getValueObject(objetoLista, textFile, record.getInnerRecordModel(), recordIterator);

			colecao.add(objetoLista);
		}

		return colecao;
	}

	private static <M> Collection<M> instanciarColecao(Field field) throws FileMapperException {

		if (List.class.isAssignableFrom(field.getType())) {
			return new ArrayList<>();
		}

		if (Set.class.isAssignableFrom(field.getType())) {
			return new HashSet<>();
		}

		throw new FileMapperException(
				String.format("O Atributo %s não está é uma coleção mapeada",
						field.getType()));
	}

	private static <M> void getValueObject(M instancia, List<String> textFile, InnerRecordModel repetableRecord, RecordIterator recordIterator)
			throws InstantiationException, IllegalAccessException, FileMapperException, FormatNotSuportedException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {

		if (repetableRecord.getInnerRecords() != null
				&& !ListUtil.isNullOrEmpty(repetableRecord.getInnerRecords().getRecords())) {

			mapObject(instancia, textFile, repetableRecord.getInnerRecords().getRecords(), recordIterator);

			return;
		}
		if (repetableRecord.getIdField() != null && textFile.size() > recordIterator.getRow()) {

			repetableRecord.getIdField().read(textFile.get(recordIterator.getRow()));

			getFieldValues(instancia, repetableRecord.getIdField());

			recordIterator.nextRow();
		}
	}

	private static <M> void getFieldValues(M instancia, IdFieldModel idField)
			throws IllegalAccessException {

		List<Field> fields = Arrays.asList(instancia.getClass().getDeclaredFields());

		setValue(idField, instancia, fields);

		for (FieldModel fieldModel : idField.getFields()) {

			setValue(fieldModel, instancia, fields);
		}
	}

	private static <M> void setValue(FieldModel idField, M instancia, List<Field> fields)
			throws IllegalAccessException {

		Optional<Field> optionalField = findField(idField, fields);

		if (optionalField.isPresent()) {

			ReflectionUtils.setValue(instancia, optionalField.get(), idField.getValue());
		}
	}

	private static Optional<Field> findField(FieldModel fieldModel, List<Field> fields) {

		return fields.stream().filter(field -> field.getName().equals(fieldModel.getName())).findFirst();
	}

}
