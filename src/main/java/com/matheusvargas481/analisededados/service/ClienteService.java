package com.matheusvargas481.analisededados.service;

import com.matheusvargas481.analisededados.domain.Cliente;
import com.matheusvargas481.analisededados.domain.DadoProcessado;
import com.matheusvargas481.analisededados.exception.LayoutDoClienteDiferenteDoEsperadoException;
import com.matheusvargas481.analisededados.strategy.MontaObjetoStrategy;
import com.matheusvargas481.analisededados.util.Separador;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ClienteService extends Separador implements MontaObjetoStrategy {
    private static Logger LOGGER = LoggerFactory.getLogger(ClienteService.class);
    private static final String MENSAGEM_DE_ERRO_NO_LAYOUT_DO_CLIENTE = "Não foi possível montar o cliente: {} pelo motivo: {}";

    @Override
    public void montarObjeto(String linhaComCliente, DadoProcessado dadoProcessado) {
        if (!linhaComCliente.isEmpty()) {
            String[] linhasDeClienteSemSeparador = separarLinhaParaMontarObjeto(linhaComCliente);

            try {
                if (linhasDeClienteSemSeparador.length == 4) {
                    Cliente cliente = new Cliente();
                    cliente.setCnpj(linhasDeClienteSemSeparador[1]);
                    cliente.setNome(linhasDeClienteSemSeparador[2]);
                    cliente.setAreaDeNegocio(linhasDeClienteSemSeparador[3]);
                    dadoProcessado.addCliente(cliente);

                } else
                    throw new LayoutDoClienteDiferenteDoEsperadoException();

            } catch (LayoutDoClienteDiferenteDoEsperadoException e) {
                LOGGER.error(MENSAGEM_DE_ERRO_NO_LAYOUT_DO_CLIENTE, linhaComCliente, e.getCause());
            }
        }
    }
}
