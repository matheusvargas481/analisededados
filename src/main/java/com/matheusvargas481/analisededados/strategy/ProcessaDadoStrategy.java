package com.matheusvargas481.analisededados.strategy;

import com.matheusvargas481.analisededados.domain.DadoProcessado;

public class ProcessaDadoStrategy {
    private MontaObjetoStrategy montaObjetoStrategy;

    public void setMontaObjetoStrategy(MontaObjetoStrategy montaObjetoStrategy) {
        this.montaObjetoStrategy = montaObjetoStrategy;
    }

    public void executarStrategy(String linhaDoArquivo, DadoProcessado dadoProcessado) {
        this.montaObjetoStrategy.montarObjeto(linhaDoArquivo, dadoProcessado);
    }
}
