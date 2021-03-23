package com.matheusvargas481.analisededados.exception;

public class LayoutDoClienteDiferenteDoEsperadoException extends RuntimeException {

    public LayoutDoClienteDiferenteDoEsperadoException() {
        super("Erro ao montar tentar montar o cliente.");
    }
}
