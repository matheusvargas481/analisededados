package com.matheusvargas481.analisededados.service;

import com.matheusvargas481.analisededados.domain.Cliente;
import com.matheusvargas481.analisededados.domain.DadoProcessado;
import org.junit.After;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
public class ClienteServiceTest {
    @Autowired
    private ClienteService clienteService;

    @MockBean
    private Logger logger;

    private DadoProcessado dadoProcessado = new DadoProcessado();

    @After
    public void teardown() {
        logger.info("teardown");
        dadoProcessado = new DadoProcessado();
    }

    @Test
    public void testProcessarLinhaDeCliente() {
        clienteService.processarLinha(getLinhaDeCliente(), dadoProcessado);
        assertEquals(getCliente(), dadoProcessado.getClientes().get(0));
    }

    @Test
    public void testProcessarLinhaDeClienteVazia() {
        clienteService.processarLinha(getLinhaDeClienteVazia(), dadoProcessado);
        assertTrue(dadoProcessado.getClientes().isEmpty());
    }

    @Test
    public void testProcessarLinhaDeClienteComSeparadorDiferente() {
        clienteService.processarLinha(getLinhaDeClienteComOutroSeparador(), dadoProcessado);
        assertEquals(getCliente(), dadoProcessado.getClientes().get(0));
    }

    @Test
    public void testProcessarLinhaDeClienteContendoCedilhaNoNome() {
        clienteService.processarLinha(getLinhaDeClienteContendoCedilhaNoNome(), dadoProcessado);
        assertEquals(getClienteContendoCedilhaNoNome(), dadoProcessado.getClientes().get(0));
    }

    @Test
    public void testProcessarLinhaDeClienteComColunasIncompletas() {
        clienteService.processarLinha(getLinhaDeClienteComColunasIncompletas(), dadoProcessado);
        assertTrue(dadoProcessado.getClientes().isEmpty());
    }


    private String getLinhaDeCliente() {
        return "002ç2345675433444345çEduardo PereiraçRural";
    }

    private Cliente getCliente() {
        return new Cliente("2345675433444345", "Eduardo Pereira", "Rural");
    }

    private String getLinhaDeClienteVazia() {
        return "";
    }


    private String getLinhaDeClienteComOutroSeparador() {
        return "002;2345675433444345;Eduardo Pereira;Rural";
    }

    private String getLinhaDeClienteComColunasIncompletas() {
        return "002ç2345675437777777ççRural";
    }

    private String getLinhaDeClienteContendoCedilhaNoNome() {
        return "002ç2345675433444345çConceiçãoçRural";
    }

    private Cliente getClienteContendoCedilhaNoNome() {
        return new Cliente("2345675433444345", "Conceição", "Rural");
    }

}
