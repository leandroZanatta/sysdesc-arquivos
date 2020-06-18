package br.com.sysdesc.arquivos.mocks;

import java.util.List;

import br.com.sysdesc.arquivos.metamodel.GroupOfRecords;
import br.com.sysdesc.arquivos.metamodel.Record;
import br.com.sysdesc.arquivos.metamodel.RootFile;
import lombok.Data;

@RootFile
@GroupOfRecords(name = "arquivoRetorno")
@Data
public class Retorno {

	@Record(order = 0)
	private RetornoHeaderArquivo headerArquivo;

	@Record(order = 1)
	private RetornoHeaderLote headerLote;

	@Record(order = 2)
	private List<RetornoDetalhe> detalhe;

	@Record(order = 3)
	private RetornoTraillerLote traillerLote;

	@Record(order = 4)
	private RetornoTraillerArquivo traillerArquivo;

}
