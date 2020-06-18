package br.com.sysdesc.arquivos.core;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import br.com.sysdesc.arquivos.mocks.Retorno;

@RunWith(MockitoJUnitRunner.class)
public class FileManagerTest {

	@Test()
	public void testarProcessamentoDiarioSemPacientes() throws Exception {

		Retorno modelo = FileManager.readFile(new File(FileManagerTest.class.getClassLoader().getResource("sicoob.ret").toURI()), Retorno.class);

		assertEquals(0, modelo.getDetalhe().size());
	}

}
