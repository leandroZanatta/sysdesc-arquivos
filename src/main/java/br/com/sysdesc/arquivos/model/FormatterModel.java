package br.com.sysdesc.arquivos.model;

import java.io.Serializable;

import br.com.sysdesc.arquivos.formatters.enumerators.FormatterEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FormatterModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private FormatterEnum formatterEnum;

	private String value;
}
