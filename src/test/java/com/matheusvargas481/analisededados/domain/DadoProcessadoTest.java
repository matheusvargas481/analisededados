package com.matheusvargas481.analisededados.domain;

import com.matheusvargas481.analisededados.service.ClienteService;
import com.matheusvargas481.analisededados.service.VendaService;
import com.matheusvargas481.analisededados.service.VendedorService;
import org.junit.After;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class DadoProcessadoTest {

    @Autowired
    private VendedorService vendedorService;
    @Autowired
    private VendaService vendaService;
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
    public void testBuscarQuantidadeDeClienteNoArquivo() {
        clienteService.processarLinha(getLinhaDeCliente(), dadoProcessado);
        assertEquals(1, dadoProcessado.buscarQuantidadeDeClientes());
    }

    @Test
    public void testBuscarQuantidadeDeClienteLinhaArquivoVazia() {
        clienteService.processarLinha(getArquivoVazio(), dadoProcessado);
        assertEquals(0, dadoProcessado.buscarQuantidadeDeClientes());
    }

    @Test
    public void testBuscarQuantidadeDeVendedoresNoArquivo() {
        vendedorService.processarLinha(getLinhaDeVendedor(), dadoProcessado);
        assertEquals(1, dadoProcessado.buscarQuantidadeDeVendedores());
    }

    @Test
    public void testBuscarQuantidadeDeVendedoresLinhaVazia() {
        vendedorService.processarLinha(getArquivoVazio(), dadoProcessado);
        assertEquals(0, dadoProcessado.buscarQuantidadeDeVendedores());
    }


    @Test
    public void testBuscarIdDaVendaDeMaiorValor() {
        vendaService.processarLinha(getLinhaDeVenda(), dadoProcessado);
        assertEquals(10L, dadoProcessado.buscarIdDaVendaDeMaiorValor());
    }

    @Test
    public void testBuscarIdDaVendaDeMaiorValorComLinhaVazia() {
        vendaService.processarLinha(getArquivoVazio(), dadoProcessado);
        assertEquals(0L, dadoProcessado.buscarIdDaVendaDeMaiorValor());
    }

    @Test
    public void testBuscarPiorVendedor() {
        vendaService.processarLinha(getLinhaDeVenda(), dadoProcessado);
        assertEquals("Diego", dadoProcessado.buscarPiorVendedor());
    }

    @Test
    public void testBuscarPiorVendedorComLinhaVazia() {
        vendaService.processarLinha(getArquivoVazio(), dadoProcessado);
        assertEquals("", dadoProcessado.buscarPiorVendedor());
    }


    private String getLinhaDeCliente() {
        return "002ç2345675433444345çEduardo PereiraçRural";
    }

    private String getArquivoVazio() {
        return "";
    }

    private String getLinhaDeVendedor() {
        return "001ç1234567891234çDiegoç50000";
    }

    private String getLinhaDeVenda() {
        return "003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çDiego";
    }
}