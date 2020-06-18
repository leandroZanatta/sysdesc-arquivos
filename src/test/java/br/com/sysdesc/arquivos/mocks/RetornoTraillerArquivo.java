package br.com.sysdesc.arquivos.mocks;

import br.com.sysdesc.arquivos.metamodel.Field;
import br.com.sysdesc.arquivos.metamodel.IgnoreId;
import lombok.Data;

@Data
@IgnoreId(type = String.class, name = "tipoRegistro", start = 7, end = 8, defaultValue = "5")
public class RetornoTraillerArquivo {

	@Field(order = 1, start = 17, end = 27)
	private Long quantidadeRegistros;

}
