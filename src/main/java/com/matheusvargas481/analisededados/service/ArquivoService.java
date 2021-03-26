package com.matheusvargas481.analisededados.service;

import com.matheusvargas481.analisededados.config.GerenciaDiretorioConfig;
import com.matheusvargas481.analisededados.domain.DadoFinalParaEscritaNoArquivo;
import com.matheusvargas481.analisededados.domain.DadoProcessado;
import com.matheusvargas481.analisededados.exception.ErroAoLerEProcessarLinhaArquivoException;
import com.matheusvargas481.analisededados.exception.ErroNaCriacaoDoArquivoException;
import com.matheusvargas481.analisededados.exception.ErroNaEscritaDoArquivoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

@Service
public class ArquivoService {
    @Autowired
    private ProcessaLinhaService processaLinhaService;

    public void lerEprocessarLinhas() {
        try {
            DadoProcessado dadoProcessado = new DadoProcessado();
            for (Path path : Files.newDirectoryStream(Paths.get(GerenciaDiretorioConfig.DIRETORIO_DE_ENTRADA),
                    path -> path.toFile().isFile())) {
                try (Stream<String> linhas = Files.lines(Paths.get(path.normalize().toString()))) {
                    linhas.forEach(linha -> processaLinhaService.processarLinha(linha, dadoProcessado));
                }
            }
            escreverDadoProcessadoNoArquivo(dadoProcessado);
        } catch (IOException e) {
            throw new ErroAoLerEProcessarLinhaArquivoException(e.getMessage());
        }
    }

    private void escreverDadoProcessadoNoArquivo(DadoProcessado dadoProcessado) {
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