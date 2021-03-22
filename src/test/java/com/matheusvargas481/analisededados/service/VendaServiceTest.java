package com.matheusvargas481.analisededados.service;

import com.matheusvargas481.analisededados.domain.ItemDeVenda;
import com.matheusvargas481.analisededados.domain.Venda;
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
public class VendaServiceTest {

    @Autowired
    private VendaService vendaService;

    @MockBean
    private Logger logger;

    private List<Venda> vendas;

    @Before
    public void init() {
        logger.info("startup");
        vendas = new ArrayList<>();
    }

    @After
    public void teardown() {
        logger.info("teardown");
        vendas.clear();
    }

    @Test
    public void testProcessarLinhasComVendas() {
        vendas = vendaService.processarLinhasComVendas(getLinhasDoArquivo());
        assertEquals(getVendas(), vendas);
    }

    @Test
    public void testArquivoVazio() {
        vendas = vendaService.processarLinhasComVendas(getArquivoVazio());
        assertEquals(Collections.EMPTY_LIST, vendas);
    }

    @Test
    public void testArquivoValidoESemLinhasDeVenda() {
        vendas = vendaService.processarLinhasComVendas(getLinhasDoArquivoSemVendas());
        assertEquals(Collections.EMPTY_LIST, vendas);
    }

    @Test
    public void testComSeparadorDiferente() {
        vendas = vendaService.processarLinhasComVendas(getLinhasDeVendasComOutroSeparador());
        assertEquals(getVendas(), vendas);
    }

    @Test
    public void testNomeComCedilha() {
        vendas = vendaService.processarLinhasComVendas(getLinhasDeVendaContendoCedilhaNoNomeDoVendedor());
        assertEquals(getVendasComCedilhaNoNomeDoVendedor(), vendas);
    }

    @Test
    public void testMontarVendaComColunasIncompletas() {
        vendas = vendaService.processarLinhasComVendas(getLinhasDeVendasComCamposIncompletos());
        assertEquals(getVendasComDadosFaltando(), vendas);
    }

    @Test
    public void testMontarItemDeVendaColunasDivergentesDoEsperado() {
        vendas = vendaService.processarLinhasComVendas(getLinhasDeVendaContendoDivergenciaNoItemDeVenda());
        assertEquals(getVendasComItensDeVendasComDivergencia(), vendas);
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


    private List<String> getLinhasDeVendasComOutroSeparador() {
        return Arrays.asList(
                "003;10;[1-10-100,2-30-2.50,3-40-3.10];Diego",
                "003;08;[1-34-10,2-33-1.50,3-40-0.10];Renato");
    }

    private List<String> getLinhasDeVendasComCamposIncompletos() {
        return Arrays.asList(
                "003ç10ççDiego",
                "003ç08ç[1-34-10,2-33-1.50,3-40-0.10]ç",
                "003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çRenato");
    }

    private List<String> getLinhasDoArquivoSemVendas() {
        return Arrays.asList(
                "001ç1234567891234çDiegoç50000",
                "001ç3245678865434çRenatoç40000.99",
                "002ç2345675434544345çJose da SilvaçRural",
                "002ç2345675433444345çEduardo PereiraçRural");
    }

    private List<String> getArquivoVazio() {
        return Arrays.asList("");
    }

    private List<Venda> getVendas() {
        Venda vendaEsperadaUm = new Venda(10L, Arrays.asList(
                new ItemDeVenda(1L, 10, 100D),
                new ItemDeVenda(2L, 30, 2.50),
                new ItemDeVenda(3L, 40, 3.10)),
                "Diego");
        Venda vendaEsperadaDois = new Venda(8L, Arrays.asList(
                new ItemDeVenda(1L, 34, 10D),
                new ItemDeVenda(2L, 33, 1.50),
                new ItemDeVenda(3L, 40, 0.10)),
                "Renato");
        return Arrays.asList(vendaEsperadaUm, vendaEsperadaDois);
    }

    private List<String> getLinhasDeVendaContendoCedilhaNoNomeDoVendedor() {
        return Arrays.asList(
                "003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çAssunção",
                "003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çConceição");
    }

    private List<String> getLinhasDeVendaContendoDivergenciaNoItemDeVenda() {
        return Arrays.asList(
                "003ç10ç[1-8-10-100-50,2-30-2.-650,3-4-0-3.10]çDiego",
                "003ç08ç[1-3-4-10,2-33-1.50-8-5,3-4-0-0.10]çRenato",
                "003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çRenato");
    }

    private List<Venda> getVendasComCedilhaNoNomeDoVendedor() {
        Venda vendaEsperadaUm = new Venda(10L, Arrays.asList(
                new ItemDeVenda(1L, 10, 100D),
                new ItemDeVenda(2L, 30, 2.50),
                new ItemDeVenda(3L, 40, 3.10)),
                "Assunção");
        Venda vendaEsperadaDois = new Venda(8L, Arrays.asList(
                new ItemDeVenda(1L, 34, 10D),
                new ItemDeVenda(2L, 33, 1.50),
                new ItemDeVenda(3L, 40, 0.10)),
                "Conceição");
        return Arrays.asList(vendaEsperadaUm, vendaEsperadaDois);
    }

    private List<Venda> getVendasComDadosFaltando() {
        Venda vendaEsperada = new Venda(8L, Arrays.asList(
                new ItemDeVenda(1L, 34, 10D),
                new ItemDeVenda(2L, 33, 1.50),
                new ItemDeVenda(3L, 40, 0.10)),
                "Renato");

        return Arrays.asList(vendaEsperada);
    }

    private List<Venda> getVendasComItensDeVendasComDivergencia() {
        Venda vendaEsperadaUm = new Venda(10L, Collections.EMPTY_LIST, "Diego");
        Venda vendaEsperadaDois = new Venda(8L, Collections.EMPTY_LIST, "Renato");
        Venda vendaEsperadaTres = new Venda(8L, Arrays.asList(
                new ItemDeVenda(1L, 34, 10D),
                new ItemDeVenda(2L, 33, 1.50),
                new ItemDeVenda(3L, 40, 0.10)),
                "Renato");

        return Arrays.asList(vendaEsperadaUm, vendaEsperadaDois, vendaEsperadaTres);
    }
}