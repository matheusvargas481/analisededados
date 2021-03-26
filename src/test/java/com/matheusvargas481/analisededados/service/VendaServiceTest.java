package com.matheusvargas481.analisededados.service;

import com.matheusvargas481.analisededados.domain.DadoProcessado;
import com.matheusvargas481.analisededados.domain.ItemDeVenda;
import com.matheusvargas481.analisededados.domain.Venda;
import org.junit.After;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertTrue;


@SpringBootTest
@ActiveProfiles("test")
public class VendaServiceTest {

    @Autowired
    private VendaService vendaService;

    @MockBean
    private Logger logger;

    private DadoProcessado dadoProcessado = new DadoProcessado();

    @After
    public void teardown() {
        logger.info("teardown");
        dadoProcessado = new DadoProcessado();
    }


    @Test
    public void testProcessarLinhaDeVenda() {
        vendaService.processarLinha(getLinhaDeVenda(), dadoProcessado);
        Assert.assertEquals(getVenda(), dadoProcessado.getVendas().get(0));
    }

    @Test
    public void testProcessarLinhaDeVendaVazia() {
        vendaService.processarLinha(getLinhaDeVendaVazia(), dadoProcessado);
        assertTrue(dadoProcessado.getVendas().isEmpty());
    }

    @Test
    public void testProcessarLinhaDeVendaComSeparadorDiferente() {
        vendaService.processarLinha(getLinhaDeVendaComOutroSeparador(), dadoProcessado);
        Assert.assertEquals(getVenda(), dadoProcessado.getVendas().get(0));
    }

    @Test
    public void testProcessarLinhaDeVendaContendoCedilhaNoNomeDoVendedor() {
        vendaService.processarLinha(getLinhaDeVendaContendoCedilhaNoNomeDoVendedor(), dadoProcessado);
        Assert.assertEquals(getVendaContendoCedilhaNoNomeDoVendedor(), dadoProcessado.getVendas().get(0));
    }

    @Test
    public void testProcessarLinhaDeVendaComColunasIncompletas() {
        vendaService.processarLinha(getLinhaDeVendaComColunasIncompletas(), dadoProcessado);
        assertTrue(dadoProcessado.getClientes().isEmpty());
    }

    @Test
    public void testMontarItemDeVendaColunasDivergentesDoEsperado() {
        vendaService.processarLinha(getLinhaDeVendaContendoDivergenciaNoItemDeVenda(), dadoProcessado);
        assertTrue(dadoProcessado.getVendas().get(0).getItensDeVendas().isEmpty());
    }


    private String getLinhaDeVenda() {
        return "003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çDiego";
    }

    private Venda getVenda() {
        return new Venda(10L, Arrays.asList(
                new ItemDeVenda(1L, 10, 100D),
                new ItemDeVenda(2L, 30, 2.50),
                new ItemDeVenda(3L, 40, 3.10)),
                "Diego");
    }

    private String getLinhaDeVendaVazia() {
        return "";
    }


    private String getLinhaDeVendaComOutroSeparador() {
        return "003;10;[1-10-100,2-30-2.50,3-40-3.10];Diego";
    }

    private String getLinhaDeVendaContendoCedilhaNoNomeDoVendedor() {
        return "003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çAssunção";
    }

    private Venda getVendaContendoCedilhaNoNomeDoVendedor() {
        return new Venda(10L, Arrays.asList(
                new ItemDeVenda(1L, 10, 100D),
                new ItemDeVenda(2L, 30, 2.50),
                new ItemDeVenda(3L, 40, 3.10)),
                "Assunção");

    }

    private String getLinhaDeVendaComColunasIncompletas() {
        return "003çç[1-34-10,2-33-1.50,3-40-0.10]çRenato";
    }

    private String getLinhaDeVendaContendoDivergenciaNoItemDeVenda() {
        return "003ç10ç[1-8-10-100-50,2-30-2.-650,3-4-0-3.10]çDiego";
    }


    private List<String> getLinhasDoArquivoSemVendas() {
        return Arrays.asList(
                "001ç1234567891234çDiegoç50000",
                "001ç3245678865434çRenatoç40000.99",
                "002ç2345675434544345çJose da SilvaçRural",
                "002ç2345675433444345çEduardo PereiraçRural");
    }


    private List<String> getLinhasDeVendaContendoDivergenciaNoItemDeVenda() {
        return Arrays.asList(
                "003ç10ç[1-8-10-100-50,2-30-2.-650,3-4-0-3.10]çDiego",
                "003ç08ç[1-3-4-10,2-33-1.50-8-5,3-4-0-0.10]çRenato",
                "003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çRenato");
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