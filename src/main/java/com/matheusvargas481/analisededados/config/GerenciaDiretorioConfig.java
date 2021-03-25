package com.matheusvargas481.analisededados.config;

import com.matheusvargas481.analisededados.exception.ErroNaCriacaoDoDiretorioException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.NoSuchElementException;

@Configuration
public class GerenciaDiretorioConfig {

    public static final String DIRETORIO_RAIZ = System.getProperty("user.home");
    public static final String DIRETORIO_DE_ENTRADA = DIRETORIO_RAIZ + "/data/in";
    public static final String DIRETORIO_DE_SAIDA = DIRETORIO_RAIZ + "/data/out";
    public static final String EXTENSAO_ARQUIVO = "dat";
    private static Logger LOGGER = LoggerFactory.getLogger(GerenciaDiretorioConfig.class);

    public void criarDiretorioDeEntrada() {
        Path diretorioDeEntrada = Paths.get(DIRETORIO_DE_ENTRADA);
        verificarSeODiretorioJaExiste(diretorioDeEntrada);
    }

    public void criaDiretorioDeSaida() {
        Path diretorioDeSaida = Paths.get(DIRETORIO_DE_SAIDA);
        verificarSeODiretorioJaExiste(diretorioDeSaida);
    }

    private void verificarSeODiretorioJaExiste(Path diretorio) {
        if (Files.exists(diretorio)) {
            LOGGER.info("Diretório já existe !");
        } else {
            try {
                Files.createDirectories(diretorio);
                LOGGER.info("Novo diretório criado com sucesso !");
            } catch (IOException | NoSuchElementException exception) {
                throw new ErroNaCriacaoDoDiretorioException();
            }
        }
    }
}
