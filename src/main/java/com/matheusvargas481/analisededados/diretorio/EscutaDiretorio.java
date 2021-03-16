package com.matheusvargas481.analisededados.diretorio;


import com.matheusvargas481.analisededados.arquivo.EscreveArquivo;
import com.matheusvargas481.analisededados.arquivo.LeArquivo;
import com.matheusvargas481.analisededados.domain.DadoProcessado;
import com.matheusvargas481.analisededados.service.ClienteService;
import com.matheusvargas481.analisededados.service.VendaService;
import com.matheusvargas481.analisededados.service.VendedorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;

@Component
public class EscutaDiretorio {
    private static Logger log = LoggerFactory.getLogger(EscutaDiretorio.class);
    @Autowired
    private GerenciaDiretorio gerenciaDiretorio;
    @Autowired
    private LeArquivo leArquivo;
    @Autowired
    private VendedorService vendedorService;
    @Autowired
    private VendaService vendaService;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private DadoProcessado dadoProcessado;
    @Autowired
    private EscreveArquivo escreveArquivo;

    @Autowired
    private Environment environment;

    private WatchService watchService = null;
    private WatchKey key;

    @Autowired
    Environment env;


    @PostConstruct
    public void escutarDiretorio() {
        if(env.acceptsProfiles("!test")) {
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

            processaArquivos();

            for (WatchEvent<?> event : key.pollEvents()) {
                log.info("Tipo de mudança :" + event.kind()
                        + ". Arquivo afetado: " + event.context() + ".");
            }
            key.reset();
        }
    }

    private void processaArquivos() {
        dadoProcessado.limparListas();
        List<String> linhasDosArquivos = leArquivo.lerArquivo();
        dadoProcessado.setClientes(clienteService.processarLinhasComClientes(linhasDosArquivos));
        dadoProcessado.setVendas(vendaService.processarLinhasComVendas(linhasDosArquivos));
        dadoProcessado.setVendedores(vendedorService.processarLinhasComVendedores(linhasDosArquivos));
        escreveArquivo.escreverNoArquivo();
    }

}
