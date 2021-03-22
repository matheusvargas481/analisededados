package com.matheusvargas481.analisededados.diretorio;

import com.matheusvargas481.analisededados.service.ProcessaDadoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.*;

@Component
public class EscutaDiretorio {
    private static Logger LOGGER = LoggerFactory.getLogger(EscutaDiretorio.class);
    @Autowired
    private GerenciaDiretorio gerenciaDiretorio;
    @Autowired
    private ProcessaDadoService processaDadoService;
    @Autowired
    private Environment environment;

    private WatchService watchService = null;
    private WatchKey key;

    @Autowired
    Environment env;


    @PostConstruct
    public void escutarDiretorio() {
        if (env.acceptsProfiles("!test")) {
            gerenciaDiretorio.criarDiretorioDeEntrada();

            gerenciaDiretorio.criaDiretorioDeSaida();

            registrarDiretorio(criarDiretorio());

            criarChaveDeObservacaoDoDiretorio();
        }
    }

    private Path criarDiretorio() {
        return Paths.get(GerenciaDiretorio.DIRETORIO_DE_ENTRADA);
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

            processaDadoService.processarArquivos();

            for (WatchEvent<?> event : key.pollEvents()) {
                LOGGER.info("Tipo de mudança :" + event.kind()
                        + ". Arquivo afetado: " + event.context() + ".");
            }
            key.reset();
        }
    }
}
