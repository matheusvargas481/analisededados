package com.matheusvargas481.analisededados.service;

import com.matheusvargas481.analisededados.domain.Vendedor;
import com.matheusvargas481.analisededados.exception.ErroAoMontarClienteException;
import com.matheusvargas481.analisededados.exception.ErroAoMontarVendedorException;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class VendedorService {
    private Logger log;
    private int contadorDeVendedor;

    public List<Vendedor> identificarVendedores(List<String> linhasDoArquivo) {
        contadorDeVendedor = 0;
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
            if (linhasDeVendedorSemSeparador.length != 4) throw new ErroAoMontarVendedorException();
            Vendedor vendedor = new Vendedor();
            vendedor.setCpf(linhasDeVendedorSemSeparador[1]);
            vendedor.setNome(linhasDeVendedorSemSeparador[2]);
            vendedor.setSalario(Double.parseDouble(linhasDeVendedorSemSeparador[3]));
            contadorDeVendedor++;

            return vendedor;

        } catch (ErroAoMontarVendedorException e) {
            log.error("Não foi possível montar o vendedor: {} pelo motivo: {}",linhaDeVendedor, e.getCause());
            return null;
        }
    }
}
