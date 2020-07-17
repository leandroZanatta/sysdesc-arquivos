package br.com.sysdesc.arquivos.core;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.io.FileUtils;

import br.com.sysdesc.arquivos.exceptions.FileMapperException;
import br.com.sysdesc.arquivos.exceptions.SysdescArquivosException;

public class FileManager {

	private FileManager() {
	}

	public static <T> T readFile(File textFile, Class<T> instance) throws SysdescArquivosException {

		try {

			return readFile(FileUtils.readLines(textFile, StandardCharsets.UTF_8), instance);

		} catch (IOException e) {

			throw new SysdescArquivosException("Falha ao ler o arquivo", e);
		}
	}

	public static <T> T readFile(List<String> textFile, Class<T> instance) throws SysdescArquivosException {

		try {

			return FileObjectReader.getObjectFromTextFile(instance, textFile, MetaModelReader.readMetaModel(instance));

		} catch (FileMapperException | InstantiationException | IllegalAccessException | SecurityException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException e) {

			throw new SysdescArquivosException("Falha ao ler o arquivo", e);
		}
	}

	public static void writeFile(File arquivo, Object objeto) throws SysdescArquivosException {

		try {

			FileObjectWriter.writeFile(arquivo, MetaModelReader.readMetaModel(objeto.getClass()), objeto);

		} catch (IOException | FileMapperException | InstantiationException | IllegalAccessException e) {

			throw new SysdescArquivosException("Falha ao escrever o arquivo", e);
		}

	}

}
