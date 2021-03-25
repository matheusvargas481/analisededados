package com.matheusvargas481.analisededados.exception;

public class LayoutDeVendaDiferenteDoEsperadoException extends RuntimeException {

    public LayoutDeVendaDiferenteDoEsperadoException() {
        super("Erro ao tentar montar a venda, layout incorreto.");
    }
}
