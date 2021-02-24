package com.matheusvargas481.analisededados.service;

import com.matheusvargas481.analisededados.domain.Vendedor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VendedorService {
    private List<String> linhasComVendedores;
    private List<Vendedor> vendedores;

    public List<Vendedor> getVendedores() {
        return vendedores;
    }

    public void identificarVendedores(List<String> vendedoresDoArquivo) {
        linhasComVendedores = new ArrayList<>();
        for (String vendedor : vendedoresDoArquivo) {
            if (vendedor.startsWith(Vendedor.COMECA_COM_001)) {
                linhasComVendedores.add(vendedor);
            }
        }
        montarVendedor(linhasComVendedores);
    }

    public int buscarQuantidadeDeVendedores() {
        return vendedores.size();
    }

    private void montarVendedor(List<String> linhasComVendedores) {
        vendedores = new ArrayList<>();
        for (String linhasDeVendedores : linhasComVendedores) {
            String separador = linhasDeVendedores.substring(3, 4);
            String[] linhasDeVendasSemSeparador = linhasDeVendedores.split(separador);
            try {
                Vendedor vendedor = new Vendedor();
                vendedor.setCpf(linhasDeVendasSemSeparador[1]);
                vendedor.setNome(linhasDeVendasSemSeparador[2]);
                vendedor.setSalario(Double.parseDouble(linhasDeVendasSemSeparador[3]));
                vendedores.add(vendedor);
            } catch (RuntimeException e) {
                //TODO tratar vendedores que contém erros
                throw new RuntimeException("Não foi possível montar o vendedor.", e.getCause());
            }
        }
    }

}
