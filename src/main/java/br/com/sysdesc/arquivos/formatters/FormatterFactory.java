package br.com.sysdesc.arquivos.formatters;

import static br.com.sysdesc.arquivos.formatters.enumerators.FormatterEnum.PADDING_LEFT;
import static br.com.sysdesc.arquivos.formatters.enumerators.FormatterEnum.PADDING_RIGHT;

import java.math.BigDecimal;
import java.util.HashMap;

import br.com.sysdesc.arquivos.exceptions.FormatNotSuportedException;
import br.com.sysdesc.arquivos.formatters.string.StringToBigDecimalPaddingLeftFormatter;
import br.com.sysdesc.arquivos.formatters.string.StringToLongPaddingLeftFormatter;
import br.com.sysdesc.arquivos.formatters.string.StringToStringPaddingLeftFormatter;
import br.com.sysdesc.arquivos.formatters.string.StringToStringPaddingRightFormatter;
import br.com.sysdesc.arquivos.model.FormatterModel;
import lombok.extern.slf4j.Slf4j;

@SuppressWarnings({ "unchecked", "rawtypes" })
@Slf4j
public class FormatterFactory {

	private static HashMap<KeyFormat, Class<? extends Formatter>> formatters = new HashMap<>();

	static {

		formatters.put(new KeyFormat(String.class, String.class, PADDING_LEFT),
				StringToStringPaddingLeftFormatter.class);
		formatters.put(new KeyFormat(String.class, String.class, PADDING_RIGHT),
				StringToStringPaddingRightFormatter.class);
		formatters.put(new KeyFormat(String.class, Long.class, PADDING_LEFT),
				StringToLongPaddingLeftFormatter.class);
		formatters.put(new KeyFormat(String.class, BigDecimal.class, PADDING_LEFT),
				StringToBigDecimalPaddingLeftFormatter.class);
	}

	private FormatterFactory() {
	}

	public static <K, T> Formatter<K, T> getFormatter(FormatterModel formatter, Class<K> fromClass, Class<T> targetClass)
			throws FormatNotSuportedException {

		KeyFormat keyFormat = new KeyFormat(fromClass, targetClass, formatter.getFormatterEnum());

		if (formatters.containsKey(keyFormat)) {

			try {

				return formatters.get(keyFormat).newInstance();
			} catch (InstantiationException | IllegalAccessException e) {

				log.error("Erro ao instanciar formatador", e);
			}
		}

		throw new FormatNotSuportedException(
				String.format("O formato para as classes %s e %s não é suportado", fromClass, targetClass));
	}

}
