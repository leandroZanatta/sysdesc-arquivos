package br.com.sysdesc.arquivos.util;

import br.com.sysdesc.arquivos.exceptions.SysdescArquivosException;

@FunctionalInterface
public interface ThrowableFunction<T, R> {
	public R apply(T t) throws SysdescArquivosException;
}