package com.matheusvargas481.analisededados.strategy;

import com.matheusvargas481.analisededados.domain.DadoProcessado;

public interface MontaObjetoStrategy {
    void montarObjeto(String linhasArquivo, DadoProcessado dadoProcessado);
}
