package com.matheusvargas481.analisededados.service;


import com.matheusvargas481.analisededados.domain.Cliente;
import com.matheusvargas481.analisededados.domain.DadoProcessado;
import com.matheusvargas481.analisededados.domain.Venda;
import com.matheusvargas481.analisededados.domain.Vendedor;
import com.matheusvargas481.analisededados.strategy.MontaObjetoStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ProcessaArquivoService {
    @Autowired
    private Map<String, MontaObjetoStrategy> strategyMap;

    public void processarArquivos(String linhaArquivo, DadoProcessado dadoProcessado) {
        strategyMap.get(Vendedor.COMECA_COM_001).montarObjeto(linhaArquivo, dadoProcessado);
        strategyMap.get(Cliente.COMECA_COM_002).montarObjeto(linhaArquivo, dadoProcessado);
        strategyMap.get(Venda.COMECA_COM_003).montarObjeto(linhaArquivo, dadoProcessado);
    }

}
