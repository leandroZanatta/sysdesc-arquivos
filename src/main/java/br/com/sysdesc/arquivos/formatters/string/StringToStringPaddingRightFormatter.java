package br.com.sysdesc.arquivos.formatters.string;

import br.com.sysdesc.arquivos.formatters.Formatter;
import br.com.sysdesc.arquivos.model.FieldModel;
import br.com.sysdesc.arquivos.util.StringUtil;

public class StringToStringPaddingRightFormatter implements Formatter<String, String> {

	@Override
	public String format(Object data, String value, FieldModel fieldModel) {

		return StringUtil.paddingRight(data.toString(), value, fieldModel.getEnd() - fieldModel.getStart());
	}

	@Override
	public String parse(String substring, String value, FieldModel fieldModel) {

		return substring.trim();
	}

}
