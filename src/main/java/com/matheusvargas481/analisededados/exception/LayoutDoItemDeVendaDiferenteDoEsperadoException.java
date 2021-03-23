package com.matheusvargas481.analisededados.exception;

public class LayoutDoItemDeVendaDiferenteDoEsperadoException extends RuntimeException {

    public LayoutDoItemDeVendaDiferenteDoEsperadoException() {
        super("Erro ao montar tentar montar o item de venda.");
    }
}
