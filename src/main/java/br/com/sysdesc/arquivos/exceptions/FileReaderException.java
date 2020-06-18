package br.com.sysdesc.arquivos.exceptions;

public class FileReaderException extends SysdescArquivosException {

	private static final long serialVersionUID = 1L;

	public FileReaderException(Exception exeption) {

		super("Não foi possivel converter o texto em objeto", exeption);
	}

}
