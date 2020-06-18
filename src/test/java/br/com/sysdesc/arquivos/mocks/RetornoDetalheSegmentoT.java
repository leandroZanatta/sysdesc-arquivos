package br.com.sysdesc.arquivos.mocks;

import java.math.BigDecimal;

import br.com.sysdesc.arquivos.metamodel.Field;
import br.com.sysdesc.arquivos.metamodel.IgnoreId;
import lombok.Data;

@Data
@IgnoreId(type = String.class, name = "tipoRegistro", start = 13, end = 14, defaultValue = "T")
public class RetornoDetalheSegmentoT {

	@Field(order = 1, start = 15, end = 17)
	private Long codigoMovimento;

	@Field(order = 2, start = 37, end = 57)
	private String nossoNumero;

	@Field(order = 3, start = 57, end = 58)
	private String codigoCarteira;

	@Field(order = 4, start = 58, end = 73)
	private String numeroDocumento;

	@Field(order = 5, start = 73, end = 81)
	private String dataVencimento;

	@Field(order = 6, start = 81, end = 96)
	private BigDecimal valorTitulo;

	@Field(order = 7, start = 105, end = 130)
	private String identificacaoTitulo;

	@Field(order = 8, start = 132, end = 133)
	private Long tipoPagador;

	@Field(order = 9, start = 188, end = 198)
	private Long inscricaoPagador;

	@Field(order = 10, start = 133, end = 148)
	private Long numeroContrato;

	@Field(order = 11, start = 213, end = 223)
	private String motivoOcorrencia;

}
