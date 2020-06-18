package br.com.sysdesc.arquivos.mocks;

import java.math.BigDecimal;

import br.com.sysdesc.arquivos.metamodel.Field;
import br.com.sysdesc.arquivos.metamodel.IgnoreId;
import lombok.Data;

@Data
@IgnoreId(type = String.class, name = "tipoRegistro", start = 13, end = 14, defaultValue = "U")
public class RetornoDetalheSegmentoU {

	@Field(order = 1, start = 15, end = 17)
	private Long codigoMovimento;

	@Field(order = 2, start = 8, end = 13)
	private Long numeroRegistro;

	@Field(order = 3, start = 17, end = 32)
	private BigDecimal acrescimos;

	@Field(order = 4, start = 32, end = 47)
	private BigDecimal descontos;

	@Field(order = 5, start = 47, end = 62)
	private BigDecimal abatimento;

	@Field(order = 6, start = 62, end = 77)
	private BigDecimal iof;

	@Field(order = 7, start = 77, end = 92)
	private BigDecimal valorPago;

	@Field(order = 8, start = 92, end = 107)
	private BigDecimal valorLiquido;

	@Field(order = 9, start = 107, end = 122)
	private BigDecimal outrasDespezas;

	@Field(order = 10, start = 122, end = 137)
	private BigDecimal outrosCreditos;

}
