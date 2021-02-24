package com.matheusvargas481.analisededados.service.diretorio;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.NoSuchElementException;

@Service
public class DiretorioService {
    public static final String CAMINHO_RAIZ = System.getProperty("user.home");
    public static final String CAMINHO_ENTRADA = CAMINHO_RAIZ + "/data/in";
    public static final String CAMINHO_SAIDA = CAMINHO_RAIZ + "/data/out";
    public static final String EXTENSAO_ARQUIVO = "dat";


    public void criarDiretorioDeEntrada() {
        Path dirPathIn = Paths.get(CAMINHO_ENTRADA);
        verificarSeOCaminhoJaExiste(dirPathIn);
    }

    public void criaDiretorioDeSaida() {
        Path caminhoDeEntrada = Paths.get(CAMINHO_SAIDA);
        verificarSeOCaminhoJaExiste(caminhoDeEntrada);
    }

    private void verificarSeOCaminhoJaExiste(Path dirPath) {
        boolean caminhoExistente = Files.exists(dirPath);
        if (caminhoExistente) {
            System.out.println("Diretório já existe !");
        } else {
            try {
                Files.createDirectories(dirPath);
                System.out.println("Novo diretório criado com sucesso !");
            } catch (IOException | NoSuchElementException ioExceptionObj) {
                throw new RuntimeException("Ocorreu um problema durante a criação da estrutura do diretório: " + ioExceptionObj.getMessage());
            }
        }
    }
}
