package com.matheusvargas481.analisededados.arquivo;

import com.matheusvargas481.analisededados.diretorio.GerenciaDiretorio;
import com.matheusvargas481.analisededados.service.ClienteService;
import com.matheusvargas481.analisededados.service.VendaService;
import com.matheusvargas481.analisededados.service.VendedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.NoSuchElementException;

@Component
public class EscreveArquivo {
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private VendedorService vendedorService;
    @Autowired
    private VendaService vendaService;

    public EscreveArquivo(ClienteService clienteService, VendedorService vendedorService, VendaService vendaService) {
        this.clienteService = clienteService;
        this.vendedorService = vendedorService;
        this.vendaService = vendaService;
    }

    public void escreverNoArquivo() {
        try {
            File arquivo = criarArquivo();

            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(arquivo));

            bufferedWriter.write("Quantidade de Clientes no Arquivo de entrada: " + clienteService.buscarQuantidadeDeClientes() + "\n");
            bufferedWriter.write("Quantidade de Vendedores no Arquivo de entrada: " + vendedorService.buscarQuantidadeDeVendedores() + "\n");
            bufferedWriter.write("Id da Venda De Maior Valor: " + vendaService.buscarIdDaVendaDeMaiorValor() + "\n");
            bufferedWriter.write("O Pior Vendedor: " + vendaService.buscarPiorVendedor() + "\n");

            bufferedWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File criarArquivo() {
        try {
            String diretorioFinal = GerenciaDiretorio.DIRETORIO_DE_SAIDA + "/out." + GerenciaDiretorio.EXTENSAO_ARQUIVO;
            File arquivo = new File(diretorioFinal);

            if (arquivo.exists()) {
                arquivo.delete();
                arquivo.createNewFile();
            }

            arquivo.createNewFile();
            return arquivo;

        } catch (IOException | NoSuchElementException e) {
            throw new RuntimeException("Falha ao escrever resultados !");
        }
    }

}
