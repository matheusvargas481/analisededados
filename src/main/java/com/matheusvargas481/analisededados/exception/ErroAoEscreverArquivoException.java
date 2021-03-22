package com.matheusvargas481.analisededados.exception;

public class ErroAoEscreverArquivoException extends RuntimeException {

    public ErroAoEscreverArquivoException(String mensagem) {
        super("Ocorreu um problema durante a escrita do arquivo. Exception: " + mensagem);
    }
}
