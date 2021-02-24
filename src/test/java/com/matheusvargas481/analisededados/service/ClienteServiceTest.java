package com.matheusvargas481.analisededados.service;

import com.matheusvargas481.analisededados.domain.Cliente;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class ClienteServiceTest {
    @Autowired
    private ClienteService clienteService;

//    @Test
//    public void testIdentificarCliente() {
//        clienteService.identificarClientes(getLinhasDoArquivo());
//        Assertions.assertEquals(getLinhasDeCliente(), linhasComClientes);
//    }
//
//    @Test
//    public void testMontarCliente() {
//        clienteService.identificarClientes(getLinhasDoArquivo());
//        clienteService.montarCliente();
//        Assertions.assertEquals(getClientes(), clienteService.getClientes());
//    }
//
//    @Test
//    public void testQuantidadeDeClientesNoArquivo() {
//        clienteService.identificarClientes(getLinhasDoArquivo());
//        clienteService.montarCliente();
//        Assertions.assertEquals(2, clienteService.buscarQuantidadeDeClientes());
//    }

    private List<String> getLinhasDoArquivo() {
        return Arrays.asList(
                "001ç1234567891234çDiegoç50000",
                "001ç3245678865434çRenatoç40000.99",
                "002ç2345675434544345çJose da SilvaçRural",
                "002ç2345675433444345çEduardo PereiraçRural",
                "003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çDiego",
                "003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çRenato");
    }

    private List<String> getLinhasDeCliente() {
        return Arrays.asList(
                "002ç2345675434544345çJose da SilvaçRural",
                "002ç2345675433444345çEduardo PereiraçRural");
    }

    private List<Cliente> getClientes() {
        Cliente clienteEsperadoUm = new Cliente("2345675434544345", "Jose da Silva", "Rural");
        Cliente clienteEsperadoDois = new Cliente("2345675433444345", "Eduardo Pereira", "Rural");
        return Arrays.asList(clienteEsperadoUm, clienteEsperadoDois);
    }

}
