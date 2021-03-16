package com.matheusvargas481.analisededados.service;

import com.matheusvargas481.analisededados.domain.DadoProcessado;
import com.matheusvargas481.analisededados.domain.Vendedor;
import com.matheusvargas481.analisededados.exception.ErroAoMontarVendedorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendedorService {
    private static Logger log = LoggerFactory.getLogger(VendedorService.class);
    private int contadorDeVendedor;

    @Autowired
    private DadoProcessado dadoProcessado;

    public void processarLinhasComVendedores(List<String> linhasDoArquivo) {
        contadorDeVendedor = 0;
        linhasDoArquivo.stream()
                .filter(this::isLinhaValidaVendedor)
                .map(this::montarVendedor);
    }

    private boolean isLinhaValidaVendedor(String vendedor) {
        return vendedor.startsWith(Vendedor.COMECA_COM_001);
    }

    public int buscarQuantidadeDeVendedores() {
        return contadorDeVendedor;
    }

    private Vendedor montarVendedor(String linhaDeVendedor) {
       // String separador = linhaDeVendedor.substring(3, 4);
        String[] linhasDeVendedorSemSeparador = linhaDeVendedor.split("/ç(?![a-zç])/");

        try {
            if (linhasDeVendedorSemSeparador.length != 4) throw new ErroAoMontarVendedorException();
            Vendedor vendedor = new Vendedor();
            vendedor.setCpf(linhasDeVendedorSemSeparador[1]);
            vendedor.setNome(linhasDeVendedorSemSeparador[2]);
            vendedor.setSalario(Double.parseDouble(linhasDeVendedorSemSeparador[3]));
            contadorDeVendedor++;
            dadoProcessado.addVendedor(vendedor);

            return vendedor;

        } catch (ErroAoMontarVendedorException e) {
            log.error("Não foi possível montar o vendedor: {} pelo motivo: {}", linhaDeVendedor, e.getCause());
            return null;
        }
    }
}
