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
        return new Venda($ -> {
            $.setId(10L);
            $.setItensDeVendas(Arrays.asList(
                    new ItemDeVenda(itemUm -> {
                        itemUm.setId(1L);
                        itemUm.setQuantidade(10);
                        itemUm.setPreco(100D);
                    }),
                    new ItemDeVenda(itemDois -> {
                        itemDois.setId(2L);
                        itemDois.setQuantidade(30);
                        itemDois.setPreco(2.50);
                    }),
                    new ItemDeVenda(itemtres -> {
                        itemtres.setId(3L);
                        itemtres.setQuantidade(40);
                        itemtres.setPreco(3.10);
                    })));
            $.setNome("Diego");
        });

    }

    private String getLinhaDeVendaComOutroSeparador() {
        return "003;10;[1-10-100,2-30-2.50,3-40-3.10];Diego";
    }

    private String getLinhaDeVendaContendoCedilhaNoNomeDoVendedor() {
        return "003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çAssunção";
    }

    private Venda getVendaContendoCedilhaNoNomeDoVendedor() {
        return new Venda($ -> {
            $.setId(10L);
            $.setItensDeVendas(Arrays.asList(
                    new ItemDeVenda(itemUm -> {
                        itemUm.setId(1L);
                        itemUm.setQuantidade(10);
                        itemUm.setPreco(100D);
                    }),
                    new ItemDeVenda(itemDois -> {
                        itemDois.setId(2L);
                        itemDois.setQuantidade(30);
                        itemDois.setPreco(2.50);
                    }),
                    new ItemDeVenda(itemtres -> {
                        itemtres.setId(3L);
                        itemtres.setQuantidade(40);
                        itemtres.setPreco(3.10);
                    })));
            $.setNome("Assunção");
        });

    }

    private String getLinhaDeVendaComColunasIncompletas() {
        return "003çç[1-34-10,2-33-1.50,3-40-0.10]çRenato";
    }

    private String getLinhaDeVendaContendoDivergenciaNoItemDeVenda() {
        return "003ç10ç[1-8-10-100-50,2-30-2.-650,3-4-0-3.10]çDiego";
    }
}