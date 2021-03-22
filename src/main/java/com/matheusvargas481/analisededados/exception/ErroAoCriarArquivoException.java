package com.matheusvargas481.analisededados.exception;

public class ErroAoCriarArquivoException extends RuntimeException {

    public ErroAoCriarArquivoException(String mensagem) {
        super("Ocorreu um problema durante a criação do arquivo. Exception: " + mensagem);
    }
}
