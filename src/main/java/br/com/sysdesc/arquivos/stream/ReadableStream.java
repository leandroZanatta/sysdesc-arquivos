package br.com.sysdesc.arquivos.stream;

import java.text.ParseException;

import br.com.sysdesc.arquivos.exceptions.FormatNotSuportedException;

public interface ReadableStream {

	public abstract void read(String data) throws FormatNotSuportedException, ParseException;

}
