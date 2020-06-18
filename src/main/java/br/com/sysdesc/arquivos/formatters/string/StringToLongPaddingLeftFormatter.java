package br.com.sysdesc.arquivos.formatters.string;

import br.com.sysdesc.arquivos.formatters.Formatter;
import br.com.sysdesc.arquivos.model.FieldModel;
import br.com.sysdesc.arquivos.util.StringUtil;

public class StringToLongPaddingLeftFormatter implements Formatter<String, Long> {

	@Override
	public String format(Object data, String value, FieldModel fieldModel) {

		return StringUtil.paddingLeft(data.toString(), value, fieldModel.getEnd() - fieldModel.getStart());
	}

	@Override
	public Long parse(String substring, String value, FieldModel fieldModel) {

		return Long.valueOf(substring.trim());
	}

}
