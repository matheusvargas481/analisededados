package com.matheusvargas481.analisededados.service;

import com.matheusvargas481.analisededados.DadoProcessado;
import com.matheusvargas481.analisededados.arquivo.EscreveArquivo;
import com.matheusvargas481.analisededados.arquivo.LeArquivo;
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
    private EscreveArquivo escreveArquivo;
    @Autowired
    private DadoProcessado dadoProcessado;
    @Autowired
    private LeArquivo leArquivo;

    public void processarArquivos() {
        limparListasDadoProcessado();
        processarLinhasDosArquivos(leArquivo.lerArquivo());
        escreverResultadoFinalNoArquivo();
    }

    private void processarLinhasDosArquivos(List<String> linhasDosArquivos) {
        dadoProcessado.setClientes(clienteService.processarLinhasComClientes(linhasDosArquivos));
        dadoProcessado.setVendas(vendaService.processarLinhasComVendas(linhasDosArquivos));
        dadoProcessado.setVendedores(vendedorService.processarLinhasComVendedores(linhasDosArquivos));
    }

    private void limparListasDadoProcessado(){
        dadoProcessado.limparListas();
    }

    private void escreverResultadoFinalNoArquivo(){
        escreveArquivo.escreverNoArquivo();
    }
}
