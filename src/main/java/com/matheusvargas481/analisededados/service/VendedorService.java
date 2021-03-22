package com.matheusvargas481.analisededados.service;

import com.matheusvargas481.analisededados.domain.Vendedor;
import com.matheusvargas481.analisededados.exception.ErroAoMontarVendedorException;
import com.matheusvargas481.analisededados.util.Separador;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.PublicKey;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class VendedorService extends Separador {
    private static final String REGEX_RETIRA_LETRAS_CAMPO_SALARIO = "[^\\d\\.]+";
    private static Logger LOGGER = LoggerFactory.getLogger(VendedorService.class);

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

    private Vendedor montarVendedor(String linhaComVendedor) {

        String[] linhasDeVendedorSemSeparador = separarLinhaParaMontarObjeto(linhaComVendedor);

        try {
            if (linhasDeVendedorSemSeparador.length == 4) {
                Vendedor vendedor = new Vendedor();
                vendedor.setCpf(linhasDeVendedorSemSeparador[1]);
                vendedor.setNome(linhasDeVendedorSemSeparador[2]);
                vendedor.setSalario(Double.parseDouble(removerLetrasDoSalario(linhasDeVendedorSemSeparador[3])));

                return vendedor;
            }
            throw new ErroAoMontarVendedorException();

        } catch (ErroAoMontarVendedorException e) {
            LOGGER.error("Não foi possível montar o vendedor: {} pelo motivo: {}", linhaComVendedor, e.getCause());
            return null;
        }
    }

    public String removerLetrasDoSalario(String salario){
        return salario.replaceAll(REGEX_RETIRA_LETRAS_CAMPO_SALARIO, "");
    }
}
