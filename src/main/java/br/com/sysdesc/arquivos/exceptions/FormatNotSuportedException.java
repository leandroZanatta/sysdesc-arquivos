package br.com.sysdesc.arquivos.exceptions;

public class FormatNotSuportedException extends SysdescArquivosException {

	private static final long serialVersionUID = 1L;

	public FormatNotSuportedException(String type) {

		super(type);
	}

}
