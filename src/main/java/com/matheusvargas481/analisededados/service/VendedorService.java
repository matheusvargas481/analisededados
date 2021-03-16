package com.matheusvargas481.analisededados.service;

import com.matheusvargas481.analisededados.domain.Vendedor;
import com.matheusvargas481.analisededados.exception.ErroAoMontarVendedorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class VendedorService {
    private static Logger log = LoggerFactory.getLogger(VendedorService.class);

    public List<Vendedor> processarLinhasComVendedores(List<String> linhasDoArquivo) {
        return linhasDoArquivo.stream()
                .filter(this::isLinhaValidaVendedor)
                .map(this::montarVendedor)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private boolean isLinhaValidaVendedor(String vendedor) {
        return vendedor.startsWith(Vendedor.COMECA_COM_001);
    }

    private Vendedor montarVendedor(String linhaDeVendedor) {
        String separador = linhaDeVendedor.substring(3, 4);
        if (separador.equalsIgnoreCase("ç"))
            separador = "ç(?![a-zç])";
        String[] linhasDeVendedorSemSeparador = linhaDeVendedor.split(separador);

        try {
            if (linhasDeVendedorSemSeparador.length != 4) throw new ErroAoMontarVendedorException();
            Vendedor vendedor = new Vendedor();
            vendedor.setCpf(linhasDeVendedorSemSeparador[1]);
            vendedor.setNome(linhasDeVendedorSemSeparador[2]);
            vendedor.setSalario(Double.parseDouble(linhasDeVendedorSemSeparador[3]));

            return vendedor;

        } catch (ErroAoMontarVendedorException e) {
            log.error("Não foi possível montar o vendedor: {} pelo motivo: {}", linhaDeVendedor, e.getCause());
            return null;
        }
    }
}
