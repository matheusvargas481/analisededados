package com.matheusvargas481.analisededados.exception;

public class ErroNaEscritaDoArquivoException extends RuntimeException {

    public ErroNaEscritaDoArquivoException(String mensagem) {
        super("Ocorreu um problema durante a escrita do arquivo. Exception: " + mensagem);
    }
}
