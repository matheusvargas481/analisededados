package com.matheusvargas481.analisededados.service;

import com.matheusvargas481.analisededados.domain.Cliente;
import com.matheusvargas481.analisededados.domain.DadoProcessado;
import com.matheusvargas481.analisededados.exception.LayoutDoClienteDiferenteDoEsperadoException;
import com.matheusvargas481.analisededados.strategy.ProcessaLinhaStrategy;
import com.matheusvargas481.analisededados.util.Separador;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service(value = "002")
public class ClienteService extends Separador implements ProcessaLinhaStrategy {
    private static Logger LOGGER = LoggerFactory.getLogger(ClienteService.class);
    private static final String MENSAGEM_DE_ERRO_NO_LAYOUT_DO_CLIENTE = "Não foi possível montar o cliente: {} pelo motivo: {}";

    @Override
    public void processarLinha(String linhaDeCliente, DadoProcessado dadoProcessado) {

        String[] clienteSemSeparador = separarLinhaParaMontarObjeto(linhaDeCliente);

        try {
            if (clienteSemSeparador.length == 4) {
                Cliente cliente = new Cliente();
                cliente.setCnpj(clienteSemSeparador[1]);
                cliente.setNome(clienteSemSeparador[2]);
                cliente.setAreaDeNegocio(clienteSemSeparador[3]);
                dadoProcessado.addCliente(cliente);

            } else
                throw new LayoutDoClienteDiferenteDoEsperadoException();

        } catch (LayoutDoClienteDiferenteDoEsperadoException e) {
            LOGGER.error(MENSAGEM_DE_ERRO_NO_LAYOUT_DO_CLIENTE, linhaDeCliente, e.getCause());
        }
    }
}

