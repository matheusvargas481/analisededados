package com.matheusvargas481.analisededados.exception;

public class LayoutDoClienteDiferenteDoEsperadoException extends RuntimeException {

    public LayoutDoClienteDiferenteDoEsperadoException() {
        super("Erro ao tentar montar o cliente, layout incorreto.");
    }
}
