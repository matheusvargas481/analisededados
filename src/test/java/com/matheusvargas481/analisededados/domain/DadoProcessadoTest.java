package com.matheusvargas481.analisededados.domain;

import com.matheusvargas481.analisededados.service.ClienteService;
import com.matheusvargas481.analisededados.service.VendaService;
import com.matheusvargas481.analisededados.service.VendedorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class DadoProcessadoTest {

    @InjectMocks
    private DadoProcessado dadoProcessado;
    @Autowired
    private VendedorService vendedorService;
    @Autowired
    private VendaService vendaService;
    @Autowired
    private ClienteService clienteService;

    @Test
    public void test() {
        dadoProcessado.setClientes(clienteService.processarLinhasComClientes(getLinhasDoArquivo()));
        Assertions.assertEquals(2, dadoProcessado.buscarQuantidadeDeClientes());
    }

    @Test
    public void testBuscarQuantidadeDeClientesNoArquivo() {
        dadoProcessado.setClientes(clienteService.processarLinhasComClientes(getLinhasDoArquivo()));
        Assertions.assertEquals(2, dadoProcessado.buscarQuantidadeDeClientes());
    }

    @Test
    public void testBuscarQuantidadeDeClientesNoArquivoSemClientes() {
        dadoProcessado.setClientes(clienteService.processarLinhasComClientes(getLinhasDoArquivoSemClientes()));
        Assertions.assertEquals(0, dadoProcessado.buscarQuantidadeDeClientes());
    }

    @Test
    public void testBuscarQuantidadeDeClientesComArquivoVazio() {
        dadoProcessado.setClientes(clienteService.processarLinhasComClientes(getArquivoVazio()));
        Assertions.assertEquals(0, dadoProcessado.buscarQuantidadeDeClientes());
    }

    @Test
    public void testBuscarQuantidadeDeVendedoresNoArquivo() {
        dadoProcessado.setVendedores(vendedorService.processarLinhasComVendedores(getLinhasDoArquivo()));
        assertEquals(2, dadoProcessado.buscarQuantidadeDeVendedores());
    }

    @Test
    public void testBuscarQuantidadeDeVendedoresNoArquivoSemVendedores() {
        dadoProcessado.setVendedores(vendedorService.processarLinhasComVendedores(getLinhasDoArquivoSemVendedores()));
        assertEquals(0, dadoProcessado.buscarQuantidadeDeVendedores());
    }

    @Test
    public void testBuscarQuantidadeDeVendedoresComArquivoVazio() {
        dadoProcessado.setVendedores(vendedorService.processarLinhasComVendedores(getArquivoVazio()));
        assertEquals(0, dadoProcessado.buscarQuantidadeDeVendedores());
    }


    @Test
    public void testBuscarIdDaVendaDeMaiorValor() {
        dadoProcessado.setVendas(vendaService.processarLinhasComVendas(getLinhasDoArquivo()));
        assertEquals(10L, dadoProcessado.buscarIdDaVendaDeMaiorValor());
    }

    @Test
    public void testBuscarOutroIdDaVendaDeMaiorValor() {
        dadoProcessado.setVendas(vendaService.processarLinhasComVendas(getLinhasDoArquivoComOutroIdDaVendaDeMaiorValorr()));
        assertEquals(99L, dadoProcessado.buscarIdDaVendaDeMaiorValor());
    }

    @Test
    public void testBuscarPiorVendedor() {
        dadoProcessado.setVendas(vendaService.processarLinhasComVendas(getLinhasDoArquivo()));
        assertEquals("Renato", dadoProcessado.buscarPiorVendedor());
    }

    @Test
    public void testBuscarOutroPiorVendedor() {
        dadoProcessado.setVendas(vendaService.processarLinhasComVendas(getLinhasDoArquivoComOutroPiorVendedor()));
        assertEquals("Matheus", dadoProcessado.buscarPiorVendedor());
    }


    private List<String> getArquivoVazio() {
        return Arrays.asList("");
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

    private List<String> getLinhasDoArquivoSemClientes() {
        return Arrays.asList(
                "001ç1234567891234çDiegoç50000",
                "001ç3245678865434çRenatoç40000.99",
                "003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çDiego",
                "003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çRenato");
    }

    private List<String> getLinhasDoArquivoSemVendedores() {
        return Arrays.asList(
                "002ç2345675434544345çJose da SilvaçRural",
                "002ç2345675433444345çEduardo PereiraçRural",
                "003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çDiego",
                "003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çRenato");
    }

    private List<String> getLinhasDoArquivoComOutroPiorVendedor() {
        return Arrays.asList(
                "003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çDiego",
                "003ç08ç[1-34-0,2-33-0,3-40-0]çMatheus");
    }

    private List<String> getLinhasDoArquivoComOutroIdDaVendaDeMaiorValorr() {
        return Arrays.asList(
                "003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çDiego",
                "003ç08ç[1-34-0,2-33-0,3-40-0]çMatheus",
                "003ç99ç[1-34-10000,2-33-10000,3-40-10000]çMatheus");
    }

}