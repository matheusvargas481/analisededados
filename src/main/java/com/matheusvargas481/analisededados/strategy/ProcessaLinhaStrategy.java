package com.matheusvargas481.analisededados.strategy;

import com.matheusvargas481.analisededados.domain.DadoProcessado;

public interface ProcessaLinhaStrategy {
    void processarLinha(String linha, DadoProcessado dadoProcessado);
}
