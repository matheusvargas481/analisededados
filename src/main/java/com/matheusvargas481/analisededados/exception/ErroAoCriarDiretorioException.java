package com.matheusvargas481.analisededados.exception;

public class ErroAoCriarDiretorioException extends RuntimeException {

    public ErroAoCriarDiretorioException() {
        super("Ocorreu um problema durante a criação da estrutura do diretório.");
    }
}
