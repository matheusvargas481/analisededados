package com.matheusvargas481.analisededados.exception;

public class ErroAoLerEProcessarLinhaArquivoException extends RuntimeException {
    public ErroAoLerEProcessarLinhaArquivoException(String mensagem) {
        super("Não foi possível fazer a leitura das linhas dos arquivos. " + mensagem);
    }
}
