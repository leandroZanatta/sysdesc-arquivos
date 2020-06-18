package br.com.sysdesc.arquivos.formatters;

import br.com.sysdesc.arquivos.formatters.enumerators.FormatterEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class KeyFormat {

	private Class<?> fromClass;

	private Class<?> targetClass;

	private FormatterEnum formatterEnum;
}
