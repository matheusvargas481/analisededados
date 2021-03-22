package com.matheusvargas481.analisededados.service;

import com.matheusvargas481.analisededados.domain.Cliente;
import com.matheusvargas481.analisededados.exception.ErroAoMontarClienteException;
import com.matheusvargas481.analisededados.util.Separador;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ClienteService extends Separador {
    private static Logger LOGGER = LoggerFactory.getLogger(ClienteService.class);

    public List<Cliente> processarLinhasComClientes(List<String> linhasDoArquivo) {
        return linhasDoArquivo.stream()
                .filter(this::isLinhaValidaCliente)
                .map(this::montarCliente)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private boolean isLinhaValidaCliente(String cliente) {
        return cliente.startsWith(Cliente.COMECA_COM_002);
    }

    private Cliente montarCliente(String linhaComCliente) {
        String[] linhasDeClienteSemSeparador = separarLinhaParaMontarObjeto(linhaComCliente);

        try {
            if (linhasDeClienteSemSeparador.length != 4) throw new ErroAoMontarClienteException();
            Cliente cliente = new Cliente();
            cliente.setCnpj(linhasDeClienteSemSeparador[1]);
            cliente.setNome(linhasDeClienteSemSeparador[2]);
            cliente.setAreaDeNegocio(linhasDeClienteSemSeparador[3]);

            return cliente;

        } catch (ErroAoMontarClienteException e) {
            LOGGER.error("Não foi possível montar o cliente: {}", linhaComCliente);
            return null;
        }
    }
}
