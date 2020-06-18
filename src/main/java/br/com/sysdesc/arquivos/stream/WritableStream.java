package br.com.sysdesc.arquivos.stream;

import br.com.sysdesc.arquivos.exceptions.FormatNotSuportedException;

public interface WritableStream {

	public abstract String write() throws FormatNotSuportedException;

}
