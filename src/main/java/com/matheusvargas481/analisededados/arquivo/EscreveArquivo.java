package com.matheusvargas481.analisededados.arquivo;

import com.matheusvargas481.analisededados.diretorio.GerenciaDiretorio;
import com.matheusvargas481.analisededados.domain.DadoFinalParaEscritaNoArquivo;
import com.matheusvargas481.analisededados.domain.DadoProcessado;
import com.matheusvargas481.analisededados.exception.ErroAoEscreverArquivoException;
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
    private DadoProcessado dadoProcessado;

    public void escreverNoArquivo() {

        DadoFinalParaEscritaNoArquivo dadoFinalParaEscritaNoArquivo = new DadoFinalParaEscritaNoArquivo();
        dadoFinalParaEscritaNoArquivo.setQuantidadeDeCliente(dadoProcessado.buscarQuantidadeDeClientes());
        dadoFinalParaEscritaNoArquivo.setQuantidadeDeVendedor(dadoProcessado.buscarQuantidadeDeVendedores());
        dadoFinalParaEscritaNoArquivo.setIdDaVendaDeMaiorValor(dadoProcessado.buscarIdDaVendaDeMaiorValor());
        dadoFinalParaEscritaNoArquivo.setNomePiorVendedor(dadoProcessado.buscarPiorVendedor());

        try {
            File arquivo = criarArquivo();

            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(arquivo));

            bufferedWriter.write("Quantidade de Clientes no Arquivo de entrada: " + dadoFinalParaEscritaNoArquivo.getQuantidadeDeCliente() + "\n");
            bufferedWriter.write("Quantidade de Vendedores no Arquivo de entrada: " + dadoFinalParaEscritaNoArquivo.getQuantidadeDeVendedor() + "\n");
            bufferedWriter.write("Id da Venda De Maior Valor: " + dadoFinalParaEscritaNoArquivo.getIdDaVendaDeMaiorValor() + "\n");
            bufferedWriter.write("O Pior Vendedor: " + dadoFinalParaEscritaNoArquivo.getNomePiorVendedor() + "\n");

            bufferedWriter.close();

        } catch (IOException e) {
            throw new ErroAoEscreverArquivoException(e.getMessage());
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
            throw new ErroAoEscreverArquivoException(e.getMessage());
        }
    }

}
