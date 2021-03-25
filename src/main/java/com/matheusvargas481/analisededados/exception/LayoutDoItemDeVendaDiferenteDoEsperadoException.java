package com.matheusvargas481.analisededados.exception;

public class LayoutDoItemDeVendaDiferenteDoEsperadoException extends RuntimeException {

    public LayoutDoItemDeVendaDiferenteDoEsperadoException() {
        super("Erro ao tentar montar o item de venda, layout incorreto.");
    }
}
