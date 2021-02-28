package com.matheusvargas481.analisededados.service;

import com.matheusvargas481.analisededados.domain.Cliente;
import com.matheusvargas481.analisededados.exception.ErroAoMontarClienteException;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ClienteService {
    private Logger log;
    private int contadorDeClientes;

    public List<Cliente> filtrarClientes(List<String> linhasDoArquivo) {
        contadorDeClientes = 0;
        return linhasDoArquivo.stream()
                .filter(this::isLinhaValidaCliente)
                .map(this::montarCliente)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public int buscarQuantidadeDeClientes() {
        return contadorDeClientes;
    }

    private boolean isLinhaValidaCliente(String cliente) {
        return cliente.startsWith(Cliente.COMECA_COM_002);
    }

    private Cliente montarCliente(String linhaDeCliente) {
        String separador = linhaDeCliente.substring(3, 4);

        String[] linhasDeClienteSemSeparador = linhaDeCliente.split(separador);

        try {
            if (linhasDeClienteSemSeparador.length != 4) throw new ErroAoMontarClienteException();
            Cliente cliente = new Cliente();
            cliente.setCnpj(linhasDeClienteSemSeparador[1]);
            cliente.setNome(linhasDeClienteSemSeparador[2]);
            cliente.setAreaDeNegocio(linhasDeClienteSemSeparador[3]);
            contadorDeClientes++;

            return cliente;

        } catch (ErroAoMontarClienteException e) {
            log.error("Não foi possível montar o cliente: {} pelo motivo: {}", linhaDeCliente, e.getCause());
            return null;
        }
    }
}
