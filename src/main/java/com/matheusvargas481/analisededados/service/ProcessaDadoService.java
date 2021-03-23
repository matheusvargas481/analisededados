package com.matheusvargas481.analisededados.service;


import com.matheusvargas481.analisededados.domain.DadoProcessado;
import com.matheusvargas481.analisededados.service.arquivo.ArquivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcessaDadoService {
    @Autowired
    private VendedorService vendedorService;
    @Autowired
    private VendaService vendaService;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private ArquivoService arquivoService;

    private DadoProcessado dadoProcessado = new DadoProcessado();

    public void processarArquivos() {
        processarLinhasDosArquivos(arquivoService.lerLinhasDoArquivo());
        escreverResultadoFinalNoArquivo();
    }

    private void processarLinhasDosArquivos(List<String> linhasDosArquivos) {
        dadoProcessado.setVendas(vendaService.processarLinhasComVendas(linhasDosArquivos));
        dadoProcessado.setClientes(clienteService.processarLinhasComClientes(linhasDosArquivos));
        dadoProcessado.setVendedores(vendedorService.processarLinhasComVendedores(linhasDosArquivos));
    }

    private void escreverResultadoFinalNoArquivo() {
        arquivoService.escreverNoArquivo(dadoProcessado);
    }

}
