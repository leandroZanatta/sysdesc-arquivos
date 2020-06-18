package br.com.sysdesc.arquivos.util;

import java.util.function.Function;

public class WrapperThrowable {

	public static <T, R> Function<T, R> wrap(ThrowableFunction<T, R> throwableFunction) {
		return t -> {
			try {
				return throwableFunction.apply(t);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}
}
