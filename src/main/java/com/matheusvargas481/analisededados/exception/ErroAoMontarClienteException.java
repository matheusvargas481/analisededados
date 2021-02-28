package com.matheusvargas481.analisededados.exception;

public class ErroAoMontarClienteException extends RuntimeException {

    public ErroAoMontarClienteException() {
        super("Erro ao montar tentar montar o cliente.");
    }
}
