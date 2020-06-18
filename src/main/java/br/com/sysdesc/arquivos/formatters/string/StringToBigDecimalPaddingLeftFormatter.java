package br.com.sysdesc.arquivos.formatters.string;

import java.math.BigDecimal;

import br.com.sysdesc.arquivos.formatters.Formatter;
import br.com.sysdesc.arquivos.model.FieldModel;
import br.com.sysdesc.arquivos.util.StringUtil;

public class StringToBigDecimalPaddingLeftFormatter implements Formatter<String, BigDecimal> {

	@Override
	public String format(Object data, String value, FieldModel fieldModel) {

		return StringUtil.paddingLeft(data.toString(), value, fieldModel.getEnd() - fieldModel.getStart());
	}

	@Override
	public BigDecimal parse(String substring, String value, FieldModel fieldModel) {

		return BigDecimal.valueOf(Double.valueOf(substring.trim()));
	}

}
