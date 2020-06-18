package br.com.sysdesc.arquivos.model;

import java.io.Serializable;

import br.com.sysdesc.arquivos.exceptions.FormatNotSuportedException;
import br.com.sysdesc.arquivos.formatters.FormatterFactory;
import lombok.Data;

@Data
public class FieldModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private Object value;

	private Class<?> clazz;

	private String name;

	private int start;

	private int end;

	private int order;

	private FormatterModel formatter;

	protected Object readValue(String dataRecord) throws FormatNotSuportedException {

		return FormatterFactory.getFormatter(this.formatter, String.class, this.clazz)
				.parse(dataRecord.substring(start, end), formatter.getValue(), this);
	}

	protected String writeValue() throws FormatNotSuportedException {

		return FormatterFactory.getFormatter(this.formatter, String.class, this.clazz)
				.format(value, formatter.getValue(), this);
	}
}
