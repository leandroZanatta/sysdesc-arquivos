package br.com.sysdesc.arquivos.formatters;

import br.com.sysdesc.arquivos.model.FieldModel;

public interface Formatter<K, T> {

	public K format(Object data, String value, FieldModel fieldModel);

	public T parse(String substring, String value, FieldModel fieldModel);
}
