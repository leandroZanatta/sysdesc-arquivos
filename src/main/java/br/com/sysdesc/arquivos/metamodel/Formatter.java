package br.com.sysdesc.arquivos.metamodel;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import br.com.sysdesc.arquivos.formatters.enumerators.FormatterEnum;

@Retention(RUNTIME)
@Target(FIELD)
public @interface Formatter {

	FormatterEnum type() default FormatterEnum.PADDING_RIGHT;

	String value() default " ";

}
