package com.matheusvargas481.analisededados.exception;

public class ErroAoMontarVendedorException extends RuntimeException {

    public ErroAoMontarVendedorException() {
        super("Erro ao montar tentar montar o vendedor.");
    }
}
