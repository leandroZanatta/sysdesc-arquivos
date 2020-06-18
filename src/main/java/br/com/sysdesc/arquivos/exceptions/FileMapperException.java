package br.com.sysdesc.arquivos.exceptions;

public class FileMapperException extends SysdescArquivosException {

	private static final long serialVersionUID = 1L;

	public FileMapperException(String message) {

		super(message);
	}

	public FileMapperException(Exception exeption) {

		super("Erro ao mapear arquivo de texto para o Objeto", exeption);
	}

}
