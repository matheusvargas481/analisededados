package com.matheusvargas481.analisededados.exception;

public class ErroAoLerArquivoException extends RuntimeException {

    public ErroAoLerArquivoException() {
        super("Não foi possível fazer a leitura dos arquivos. ");
    }
    public ErroAoLerArquivoException(String mensagem) {
        super("Não foi possível fazer a leitura dos arquivos. "+mensagem);
    }
}
