package com.matheusvargas481.analisededados.service.arquivo;

import com.matheusvargas481.analisededados.service.diretorio.DiretorioService;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Service
public class LeArquivoService {
    private List<String> linhasDoArquivo;


    public List<String> lerArquivo() {
        File dir = new File(DiretorioService.CAMINHO_ENTRADA);
        String linha;
        linhasDoArquivo = new ArrayList<>();
        try {
            for (File file : dir.listFiles()) {
                BufferedReader bufferedReader = Files.newBufferedReader(file.toPath());
                while ((linha = bufferedReader.readLine()) != null) {
                    linhasDoArquivo.add(linha);
                }
                bufferedReader.close();
            }
        } catch (IOException e) {
            throw new RuntimeException("Não foi possível fazer a leitura dos arquivos " + e);
        }

        return linhasDoArquivo;
    }


}
