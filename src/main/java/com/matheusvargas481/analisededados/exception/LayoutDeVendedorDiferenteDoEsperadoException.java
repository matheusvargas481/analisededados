package com.matheusvargas481.analisededados.exception;

public class LayoutDeVendedorDiferenteDoEsperadoException extends RuntimeException {

    public LayoutDeVendedorDiferenteDoEsperadoException() {
        super("Erro ao tentar montar o vendedor, layout incorreto.");
    }
}
