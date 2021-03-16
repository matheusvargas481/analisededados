package com.matheusvargas481.analisededados.diretorio;

import com.matheusvargas481.analisededados.exception.ErroAoCriarDiretorioException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.NoSuchElementException;

@Component
public class GerenciaDiretorio {

    public static final String DIRETORIO_RAIZ = System.getProperty("user.home");
    public static final String DIRETORIO_DE_ENTRADA = DIRETORIO_RAIZ + "/data/in";
    public static final String DIRETORIO_DE_SAIDA = DIRETORIO_RAIZ + "/data/out";
    public static final String EXTENSAO_ARQUIVO = "dat";
    private static Logger log = LoggerFactory.getLogger(GerenciaDiretorio.class);

    public void criarDiretorioDeEntrada() {
        Path path = Paths.get(DIRETORIO_DE_ENTRADA);
        verificarSeODiretorioJaExiste(path);
    }

    public void criaDiretorioDeSaida() {
        verificarSeODiretorioJaExiste(Paths.get(DIRETORIO_DE_SAIDA));
    }

    private void verificarSeODiretorioJaExiste(Path diretorio) {
        if (Files.exists(diretorio)) {
            log.info("Diretório já existe !");
        } else {
            try {
                Files.createDirectories(diretorio);
                log.info("Novo diretório criado com sucesso !");
            } catch (IOException | NoSuchElementException exception) {
                throw new ErroAoCriarDiretorioException();
            }
        }
    }
}
