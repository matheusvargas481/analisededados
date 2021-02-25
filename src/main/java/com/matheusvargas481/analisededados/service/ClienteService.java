package com.matheusvargas481.analisededados.service;

import com.matheusvargas481.analisededados.domain.Cliente;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ClienteService {
    private int contadorDeClientes;

    public List<Cliente> identificarClientes(List<String> linhasDoArquivo) {

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
            Cliente cliente = new Cliente();
            cliente.setCnpj(linhasDeClienteSemSeparador[1]);
            cliente.setNome(linhasDeClienteSemSeparador[2]);
            cliente.setAreaDeNegocio(linhasDeClienteSemSeparador[3]);
            contadorDeClientes++;

            return cliente;

            //TODO criar exception customizada
        } catch (Exception e) {

            //TODO trocar system out por log4j
            System.out.println("Não foi possível montar o cliente: " + linhaDeCliente + " pelo motivo: " + e.getCause());
            return null;
        }
    }
}
