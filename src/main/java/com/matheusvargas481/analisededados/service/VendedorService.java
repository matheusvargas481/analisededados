package com.matheusvargas481.analisededados.service;

import com.matheusvargas481.analisededados.domain.Vendedor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class VendedorService {
    private int contadorDeVendedor;

    public List<Vendedor> identificarVendedores(List<String> linhasDoArquivo) {
        return linhasDoArquivo.stream()
                .filter(this::isLinhaValidaVendedor)
                .map(this::montarVendedor)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private boolean isLinhaValidaVendedor(String vendedor) {
        return vendedor.startsWith(Vendedor.COMECA_COM_001);
    }

    public int buscarQuantidadeDeVendedores() {
        return contadorDeVendedor;
    }

    private Vendedor montarVendedor(String linhaDeVendedor) {
        String separador = linhaDeVendedor.substring(3, 4);
        String[] linhasDeVendedorSemSeparador = linhaDeVendedor.split(separador);

        try {
            Vendedor vendedor = new Vendedor();
            vendedor.setCpf(linhasDeVendedorSemSeparador[1]);
            vendedor.setNome(linhasDeVendedorSemSeparador[2]);
            vendedor.setSalario(Double.parseDouble(linhasDeVendedorSemSeparador[3]));
            contadorDeVendedor++;

            return vendedor;

            //TODO criar exception customizada
        } catch (RuntimeException e) {
            //TODO tratar vendedores que contém erros
            System.out.println("Não foi possível montar o vendedor: " + linhaDeVendedor + " pelo motivo: " + e.getCause());
            return null;
        }
    }
}
