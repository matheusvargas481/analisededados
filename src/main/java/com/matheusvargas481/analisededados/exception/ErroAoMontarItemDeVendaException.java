package com.matheusvargas481.analisededados.exception;

public class ErroAoMontarItemDeVendaException extends RuntimeException {

    public ErroAoMontarItemDeVendaException() {
        super("Erro ao montar tentar montar o item de venda.");
    }
}
