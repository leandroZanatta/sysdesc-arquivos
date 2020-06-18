package br.com.sysdesc.arquivos.exceptions;

import java.util.concurrent.ExecutionException;

public class SysdescArquivosException extends ExecutionException {

    private static final long serialVersionUID = 1L;

    public SysdescArquivosException(String motivo) {

        super(motivo);
    }

    public SysdescArquivosException(String motivo, Exception exeption) {

        super(motivo, exeption);
    }

}
