package com.matheusvargas481.analisededados.exception;

public class ErroNaCriacaoDoArquivoException extends RuntimeException {

    public ErroNaCriacaoDoArquivoException(String mensagem) {
        super("Ocorreu um problema durante a criação do arquivo. Exception: " + mensagem);
    }
}
