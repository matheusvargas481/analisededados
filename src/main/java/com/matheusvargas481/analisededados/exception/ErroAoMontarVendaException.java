package com.matheusvargas481.analisededados.exception;

public class ErroAoMontarVendaException extends RuntimeException {

    public ErroAoMontarVendaException() {
        super("Erro ao montar tentar montar a venda.");
    }
}
