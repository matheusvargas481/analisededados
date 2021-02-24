package com.matheusvargas481.analisededados.service;

import com.matheusvargas481.analisededados.domain.Cliente;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClienteService {
    private List<String> linhasComClientes;
    private List<Cliente> clientes;

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void identificarClientes(List<String> clientesDoArquivo) {
        linhasComClientes = new ArrayList<>();
        for (String cliente : clientesDoArquivo) {
            if (cliente.startsWith(Cliente.COMECA_COM_002)) {
                linhasComClientes.add(cliente);
            }
        }
        montarCliente(linhasComClientes);
    }

    public int buscarQuantidadeDeClientes() {
        return clientes.size();
    }

    private void montarCliente(List<String> linhasComClientes) {
        clientes = new ArrayList<>();
        for (String linhasDeClientes : linhasComClientes) {
            String separador = linhasDeClientes.substring(3, 4);
            String[] linhasDeClientesSemSeparador = linhasDeClientes.split(separador);
            try {
                Cliente cliente = new Cliente();
                cliente.setCnpj(linhasDeClientesSemSeparador[1]);
                cliente.setNome(linhasDeClientesSemSeparador[2]);
                cliente.setAreaDeNegocio(linhasDeClientesSemSeparador[3]);
                clientes.add(cliente);
            } catch (RuntimeException e) {
                //TODO tratar clientes que contém erros
                throw new RuntimeException("Não foi possível montar o cliente.", e.getCause());
            }
        }
    }
}
