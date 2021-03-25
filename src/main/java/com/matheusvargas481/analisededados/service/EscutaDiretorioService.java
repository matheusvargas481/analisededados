package com.matheusvargas481.analisededados.service;

import com.matheusvargas481.analisededados.config.GerenciaDiretorioConfig;
import com.matheusvargas481.analisededados.service.arquivo.ArquivoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.*;

@Service
public class EscutaDiretorioService {
    private static Logger LOGGER = LoggerFactory.getLogger(EscutaDiretorioService.class);
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
            arquivoService.lerLinhasDeTodosArquivosDoDiretorioObservado();
            for (WatchEvent<?> event : key.pollEvents()) {
                LOGGER.info("Tipo de mudança :" + event.kind()
                        + ". Arquivo afetado: " + event.context() + ".");
            }
            key.reset();
        }
    }
}
