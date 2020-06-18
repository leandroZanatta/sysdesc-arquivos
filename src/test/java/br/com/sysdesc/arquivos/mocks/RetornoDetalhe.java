package br.com.sysdesc.arquivos.mocks;

import br.com.sysdesc.arquivos.metamodel.GroupOfRecords;
import br.com.sysdesc.arquivos.metamodel.Record;
import lombok.Data;

@Data
@GroupOfRecords(name = "detalheRetorno")
public class RetornoDetalhe {

	@Record(order = 0)
	private RetornoDetalheSegmentoT retornoDetalheSegmentoT;

	@Record(order = 1)
	private RetornoDetalheSegmentoU retornoDetalheSegmentoU;

}
