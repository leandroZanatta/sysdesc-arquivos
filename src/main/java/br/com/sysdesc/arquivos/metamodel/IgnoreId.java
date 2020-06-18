package br.com.sysdesc.arquivos.metamodel;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(TYPE)
public @interface IgnoreId {

	int order() default 0;

	String defaultValue() default "";

	Formatter formatter() default @Formatter;

	Class<?> type();

	String name();

	int start();

	int end();
}
