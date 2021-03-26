package com.matheusvargas481.analisededados.service;

import com.matheusvargas481.analisededados.config.GerenciaDiretorioConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.*;

@Service
public class DiretorioService {
    private static Logger LOGGER = LoggerFactory.getLogger(DiretorioService.class);
    private static final String MENSAGEM_DE_TIPO_DE_ALTERACAO_NO_ARQUIVO_AFETADO = "Tipo de mudança : {}. Arquivo afetado: {}.";

    @Autowired
    private ArquivoService arquivoService;
    @Autowired
    private GerenciaDiretorioConfig gerenciaDiretorioConfig;
    @Autowired
    private Environment env;
    private WatchService watchService = null;
    private WatchKey key;


    @PostConstruct
    public void escutarDiretorio() {
        if (env.acceptsProfiles("!test")) {
            gerenciaDiretorioConfig.criarDiretorioDeEntrada();

            gerenciaDiretorioConfig.criaDiretorioDeSaida();

            registrarDiretorio(criarDiretorio());

            criarChaveDeObservacaoDoDiretorio();
        }
    }

    private Path criarDiretorio() {
        return Paths.get(GerenciaDiretorioConfig.DIRETORIO_DE_ENTRADA);
    }

    private void registrarDiretorio(Path caminho) {
        try {
            watchService = FileSystems.getDefault().newWatchService();
            caminho.register(
                    watchService,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE,
                    StandardWatchEventKinds.ENTRY_MODIFY);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void criarChaveDeObservacaoDoDiretorio() {
        while (true) {
            try {
                if (!((key = watchService.take()) != null)) break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            arquivoService.lerEprocessarLinhas();
            for (WatchEvent<?> event : key.pollEvents()) {
                LOGGER.info(MENSAGEM_DE_TIPO_DE_ALTERACAO_NO_ARQUIVO_AFETADO, event.kind(), event.context());
            }
            key.reset();
        }
    }
}
