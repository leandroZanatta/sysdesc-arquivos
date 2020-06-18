package br.com.sysdesc.arquivos.mocks;

import br.com.sysdesc.arquivos.metamodel.Field;
import br.com.sysdesc.arquivos.metamodel.IgnoreId;
import lombok.Data;

@Data
@IgnoreId(type = String.class, name = "tipoRegistro", start = 7, end = 8, defaultValue = "0")
public class RetornoHeaderArquivo {

	@Field(order = 5, start = 17, end = 18)
	private Long tipoInscricao;

	@Field(order = 6, start = 18, end = 32)
	private Long numeroInscricao;

	@Field(order = 7, start = 52, end = 57)
	private Long numeroAgencia;

	@Field(order = 8, start = 57, end = 58)
	private Long dvAgencia;

	@Field(order = 9, start = 58, end = 70)
	private Long numeroconta;

	@Field(order = 10, start = 70, end = 71)
	private Long dvConta;

	@Field(order = 11, start = 143, end = 151)
	private String dataArquivo;

	@Field(order = 12, start = 151, end = 157)
	private String horaArquivo;

	@Field(order = 13, start = 157, end = 163)
	private Long sequencial;
}
