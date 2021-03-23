package com.matheusvargas481.analisededados.exception;

public class ErroNaCriacaoDoDiretorioException extends RuntimeException {

    public ErroNaCriacaoDoDiretorioException() {
        super("Ocorreu um problema durante a criação da estrutura do diretório.");
    }
}
