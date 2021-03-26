package com.matheusvargas481.analisededados.service;


import com.matheusvargas481.analisededados.domain.DadoProcessado;
import com.matheusvargas481.analisededados.strategy.ProcessaLinhaStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ProcessaLinhaService {
    @Autowired
    private Map<String, ProcessaLinhaStrategy> strategyMap;

    public void processarArquivos(String linhaArquivo, DadoProcessado dadoProcessado) {
        if (!linhaArquivo.isEmpty())
            strategyMap.get(linhaArquivo.substring(0, 3)).processarLinha(linhaArquivo, dadoProcessado);
    }
}
