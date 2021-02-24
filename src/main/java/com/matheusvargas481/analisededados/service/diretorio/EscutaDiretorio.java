package com.matheusvargas481.analisededados.service.diretorio;


import com.matheusvargas481.analisededados.service.arquivo.EscreveArquivoService;
import com.matheusvargas481.analisededados.service.arquivo.LeArquivoService;
import com.matheusvargas481.analisededados.service.ClienteService;
import com.matheusvargas481.analisededados.service.VendaService;
import com.matheusvargas481.analisededados.service.VendedorService;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

public class EscutaDiretorio {
    private DiretorioService diretorioService = new DiretorioService();
    private LeArquivoService leArquivoService = new LeArquivoService();
    private VendedorService vendedorService = new VendedorService();
    private VendaService vendaService = new VendaService();
    private ClienteService clienteService = new ClienteService();
    private WatchService watchService = null;
    private WatchKey key;

    public void escutarCaminho() {
        diretorioService.criarDiretorioDeEntrada();
        diretorioService.criaDiretorioDeSaida();

        try {
            watchService = FileSystems.getDefault().newWatchService();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Path caminho = criarCaminho();

        registrarCaminho(caminho);

        criarChaveDeObservacaoDoCaminho();

    }

    private Path criarCaminho() {
        return Paths.get(DiretorioService.CAMINHO_ENTRADA);
    }

    private void registrarCaminho(Path caminho) {
        try {
            caminho.register(
                    watchService,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE,
                    StandardWatchEventKinds.ENTRY_MODIFY);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void criarChaveDeObservacaoDoCaminho() {
        EscreveArquivoService escreveArquivoService = new EscreveArquivoService(clienteService, vendedorService, vendaService);
        while (true) {
            try {
                if (!((key = watchService.take()) != null)) break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            List<String> linhasDosArquivos = leArquivoService.lerArquivo();
            clienteService.identificarClientes(linhasDosArquivos);
            vendaService.identificarVendas(linhasDosArquivos);
            vendedorService.identificarVendedores(linhasDosArquivos);
            escreveArquivoService.escreverNoArquivo();

            for (WatchEvent<?> event : key.pollEvents()) {
                System.out.println(
                        "Event kind:" + event.kind()
                                + ". File affected: " + event.context() + ".");
            }
            key.reset();
        }
    }

}
