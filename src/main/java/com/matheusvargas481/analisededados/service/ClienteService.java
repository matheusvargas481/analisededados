package com.matheusvargas481.analisededados.service;

import com.matheusvargas481.analisededados.domain.DadoProcessado;
import com.matheusvargas481.analisededados.domain.Cliente;
import com.matheusvargas481.analisededados.exception.ErroAoMontarClienteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {
    private static Logger log = LoggerFactory.getLogger(ClienteService.class);
    private int contadorDeClientes;

    @Autowired
    private DadoProcessado dadoProcessado;

    public void processarLinhasComClientes(List<String> linhasDoArquivo) {
        contadorDeClientes = 0;
        linhasDoArquivo.stream()
                .filter(this::isLinhaValidaCliente)
                .forEach(this::montarCliente);
    }

    public int buscarQuantidadeDeClientes() {
        return contadorDeClientes;
    }

    private boolean isLinhaValidaCliente(String cliente) {
        return cliente.startsWith(Cliente.COMECA_COM_002);
    }

    private Cliente montarCliente(String linhaDeCliente) {
        //String separador = linhaDeCliente.substring(3, 4);

        String[] linhasDeClienteSemSeparador = linhaDeCliente.split("/ç(?![a-zç])/");

        try {
            if (linhasDeClienteSemSeparador.length != 4) throw new ErroAoMontarClienteException();
            Cliente cliente = new Cliente();
            cliente.setCnpj(linhasDeClienteSemSeparador[1]);
            cliente.setNome(linhasDeClienteSemSeparador[2]);
            cliente.setAreaDeNegocio(linhasDeClienteSemSeparador[3]);
            dadoProcessado.addCliente(cliente);
            contadorDeClientes++;

            return cliente;

        } catch (ErroAoMontarClienteException e) {
            log.error("Não foi possível montar o cliente: {} pelo motivo: {}", linhaDeCliente, e.getCause());
            return null;
        }
    }
}
