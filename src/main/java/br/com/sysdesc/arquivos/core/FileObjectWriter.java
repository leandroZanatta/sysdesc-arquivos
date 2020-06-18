package br.com.sysdesc.arquivos.core;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.apache.commons.io.FileUtils;

import br.com.sysdesc.arquivos.exceptions.FileMapperException;
import br.com.sysdesc.arquivos.exceptions.FormatNotSuportedException;
import br.com.sysdesc.arquivos.model.FieldModel;
import br.com.sysdesc.arquivos.model.IdFieldModel;
import br.com.sysdesc.arquivos.model.RecordModel;
import br.com.sysdesc.arquivos.model.TexFileModel;
import br.com.sysdesc.arquivos.util.ReflectionUtils;

public class FileObjectWriter {

	private FileObjectWriter() {
	}

	public static <T> void writeFile(File arquivo, TexFileModel textFile, T object) throws IOException,
			FileMapperException, InstantiationException, IllegalAccessException, FormatNotSuportedException {

		List<String> lines = new ArrayList<>();

		writeRecords(lines, textFile.getGroupRecords().getRecords(), object);

		FileUtils.writeLines(arquivo, lines);
	}

	private static <T> void writeRecords(List<String> lines, List<RecordModel> records, T object)
			throws FileMapperException, InstantiationException, IllegalAccessException, FormatNotSuportedException {

		for (RecordModel record : records) {

			Field field = getFieldFromMapper(object, record);

			escreverObjeto(lines, object, field, record);
		}

	}

	private static <T> void escreverObjeto(List<String> lines, T object, Field field, RecordModel record)
			throws FileMapperException, InstantiationException, IllegalAccessException, FormatNotSuportedException {

		if (Collection.class.isAssignableFrom(field.getType())) {

			Collection<?> colecao = (Collection<?>) ReflectionUtils.getValue(field, object);

			for (Object objeto : colecao) {
				writeRecords(lines, record.getInnerRecordModel().getInnerRecords().getRecords(), objeto);
			}

			return;
		}

		lines.add(escreverObjeto(ReflectionUtils.getValue(field, object), record.getInnerRecordModel().getIdField()));

	}

	private static <T> String escreverObjeto(T object, IdFieldModel idFieldModel)
			throws IllegalAccessException, FormatNotSuportedException {

		List<Field> fields = Arrays.asList(object.getClass().getDeclaredFields());

		setValue(idFieldModel, fields, object);

		for (FieldModel fieldModel : idFieldModel.getFields()) {
			setValue(fieldModel, fields, object);
		}

		return idFieldModel.write();
	}

	private static <T> void setValue(FieldModel fieldModel, List<Field> fields, T object)
			throws IllegalAccessException {

		Optional<Field> optionalField = fields.stream().filter(field -> field.getName().equals(fieldModel.getName()))
				.findFirst();

		if (optionalField.isPresent()) {
			fieldModel.setValue(ReflectionUtils.getValue(optionalField.get(), object));
		}

	}

	private static <T> Field getFieldFromMapper(T object, RecordModel record) throws FileMapperException {

		return Arrays.asList(object.getClass().getDeclaredFields()).stream()
				.filter(field -> field.getName().equals(record.getName())).findFirst()
				.orElseThrow(() -> new FileMapperException(String.format("O Objeto %s não está mapeado na classe %s",
						record.getName(), object.getClass().getName())));
	}

}
