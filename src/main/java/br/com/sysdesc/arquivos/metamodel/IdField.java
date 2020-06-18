package br.com.sysdesc.arquivos.metamodel;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(FIELD)
public @interface IdField {

	int order() default 0;

	String defaultValue() default "";

	int start();

	int end();
}
