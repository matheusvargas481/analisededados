package com.matheusvargas481.analisededados.service;

import com.matheusvargas481.analisededados.domain.Cliente;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class ClienteServiceTest {
    @Autowired
    private ClienteService clienteService;

    @MockBean
    private Logger logger;

    private List<Cliente> clientes;

    @Before
    public void init() {
        logger.info("startup");
        clientes = new ArrayList<>();
    }

    @After
    public void teardown() {
        logger.info("teardown");
        clientes.clear();
    }

    @Test
    public void testProcessarLinhasComClientes() {
        clientes = clienteService.processarLinhasComClientes(getLinhasDoArquivo());
        assertEquals(getClientes(), clientes);
    }

    @Test
    public void testArquivoVazio() {
        clientes = clienteService.processarLinhasComClientes(getArquivoVazio());
        assertEquals(Collections.EMPTY_LIST, clientes);
    }

    @Test
    public void testArquivoValidoESemLinhasDeCliente() {
        clientes = clienteService.processarLinhasComClientes(getLinhasDoArquivoSemClientes());
        assertEquals(Collections.EMPTY_LIST, clientes);
    }

    @Test
    public void testComSeparadorDiferente() {
        clientes = clienteService.processarLinhasComClientes(getLinhasDeClientesComOutroSeparador());
        assertEquals(getClientes(), clientes);
    }

    @Test
    public void testNomeComCedilha() {
        clientes = clienteService.processarLinhasComClientes(getLinhasDeClientesContendoCedilhaNoNome());
        assertEquals(getClientesComCedilha(), clientes);
    }

    @Test
    public void testColunasIncompletas() {
        clientes = clienteService.processarLinhasComClientes(getLinhasDeClientesComCamposIncompletos());
        assertEquals(getClientesComDadosFaltando(), clientes);
    }


    private List<String> getLinhasDoArquivo() {
        return Arrays.asList(
                "001ç1234567891234çDiegoç50000",
                "001ç3245678865434çRenatoç40000.99",
                "002ç2345675434544345çJose da SilvaçRural",
                "002ç2345675433444345çEduardo PereiraçRural",
                "003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çDiego",
                "003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çRenato");
    }


    private List<String> getLinhasDeClientesComOutroSeparador() {
        return Arrays.asList(
                "002;2345675434544345;Jose da Silva;Rural",
                "002;2345675433444345;Eduardo Pereira;Rural");
    }

    private List<String> getLinhasDeClientesComCamposIncompletos() {
        return Arrays.asList(
                "002ç2345675434544345ççRural",
                "002ç2345675437777777çJose da Silvaç",
                "002ç2345675433444345çEduardo PereiraçRural");
    }

    private List<String> getLinhasDoArquivoSemClientes() {
        return Arrays.asList(
                "001ç1234567891234çDiegoç50000",
                "001ç3245678865434çRenatoç40000.99",
                "003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çDiego",
                "003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çRenato");
    }

    private List<String> getArquivoVazio() {
        return Arrays.asList("");
    }

    private List<Cliente> getClientes() {
        Cliente clienteEsperadoUm = new Cliente("2345675434544345", "Jose da Silva", "Rural");
        Cliente clienteEsperadoDois = new Cliente("2345675433444345", "Eduardo Pereira", "Rural");
        return Arrays.asList(clienteEsperadoUm, clienteEsperadoDois);
    }

    private List<String> getLinhasDeClientesContendoCedilhaNoNome() {
        return Arrays.asList(
                "002ç2345675434544345çAssunçãoçRural",
                "002ç2345675433444345çConceiçãoçRural");
    }

    private List<Cliente> getClientesComCedilha() {
        Cliente clienteEsperadoUm = new Cliente("2345675434544345", "Assunção", "Rural");
        Cliente clienteEsperadoDois = new Cliente("2345675433444345", "Conceição", "Rural");
        return Arrays.asList(clienteEsperadoUm, clienteEsperadoDois);
    }

    private List<Cliente> getClientesComDadosFaltando() {
        Cliente clienteEsperado = new Cliente("2345675433444345", "Eduardo Pereira", "Rural");
        return Arrays.asList(clienteEsperado);
    }

}
