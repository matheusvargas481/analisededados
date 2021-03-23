package com.matheusvargas481.analisededados.service.arquivo;

import com.matheusvargas481.analisededados.config.GerenciaDiretorioConfig;
import com.matheusvargas481.analisededados.domain.DadoFinalParaEscritaNoArquivo;
import com.matheusvargas481.analisededados.domain.DadoProcessado;
import com.matheusvargas481.analisededados.exception.ErroAoLerArquivoException;
import com.matheusvargas481.analisededados.exception.ErroNaCriacaoDoArquivoException;
import com.matheusvargas481.analisededados.exception.ErroNaEscritaDoArquivoException;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ArquivoService {

    public void escreverNoArquivo(DadoProcessado dadoProcessado) {
        final DadoFinalParaEscritaNoArquivo dadoFinalParaEscritaNoArquivo = new DadoFinalParaEscritaNoArquivo();

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
            throw new ErroNaEscritaDoArquivoException(e.getMessage());
        }
    }

    public List<String> lerLinhasDoArquivo() {
        File dir = new File(GerenciaDiretorioConfig.DIRETORIO_DE_ENTRADA);
        String linha;
        List<String> linhas = new ArrayList<>();
        try {
            for (File file : dir.listFiles()) {

                BufferedReader bufferedReader = Files.newBufferedReader(file.toPath());

                while ((linha = bufferedReader.readLine()) != null) {
                    linhas.add(linha);
                }

                bufferedReader.close();
            }
        } catch (IOException e) {
            throw new ErroAoLerArquivoException();
        }
        return linhas;
    }

    private File criarArquivo() {
        try {
            String diretorioFinal = GerenciaDiretorioConfig.DIRETORIO_DE_SAIDA + "/out." + GerenciaDiretorioConfig.EXTENSAO_ARQUIVO;

            File arquivo = new File(diretorioFinal);

            if (arquivo.exists()) {
                arquivo.delete();
                arquivo.createNewFile();
            }

            arquivo.createNewFile();
            return arquivo;

        } catch (IOException | NoSuchElementException e) {
            throw new ErroNaCriacaoDoArquivoException(e.getMessage());
        }
    }

}
