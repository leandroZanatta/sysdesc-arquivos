package br.com.sysdesc.arquivos.mocks;

import br.com.sysdesc.arquivos.metamodel.Field;
import br.com.sysdesc.arquivos.metamodel.IgnoreId;
import lombok.Data;

@Data
@IgnoreId(type = String.class, name = "tipoRegistro", start = 7, end = 8, defaultValue = "1")
public class RetornoHeaderLote {

	@Field(order = 5, start = 183, end = 191)
	private Long numeroRemessa;

}
