package com.matheusvargas481.analisededados.arquivo;

import com.matheusvargas481.analisededados.diretorio.GerenciaDiretorio;
import com.matheusvargas481.analisededados.exception.ErroAoLerArquivoException;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Component
public class LeArquivo {
    public List<String> lerArquivo() {
        File dir = new File(GerenciaDiretorio.DIRETORIO_DE_ENTRADA);
        String linha;
        List<String> linhasDoArquivo = new ArrayList<>();
        try {
            for (File file : dir.listFiles()) {

                BufferedReader bufferedReader = Files.newBufferedReader(file.toPath());

                while ((linha = bufferedReader.readLine()) != null) {
                    linhasDoArquivo.add(linha);
                }

                bufferedReader.close();
            }
        } catch (IOException e) {
            throw new ErroAoLerArquivoException();
        }

        return linhasDoArquivo;
    }

}
