package com.matheusvargas481.analisededados.service;

import com.matheusvargas481.analisededados.domain.DadoProcessado;
import com.matheusvargas481.analisededados.domain.Vendedor;
import org.junit.After;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.Assert.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
public class VendedorServiceTest {

    @Autowired
    private VendedorService vendedorService;

    @MockBean
    private Logger logger;

    private DadoProcessado dadoProcessado = new DadoProcessado();

    @After
    public void teardown() {
        logger.info("teardown");
        dadoProcessado = new DadoProcessado();
    }

    @Test
    public void testProcessarLinhaDeVendedor() {
        vendedorService.montarObjeto(getLinhaDeVendedor(), dadoProcessado);
        Assert.assertEquals(getVendedor(), dadoProcessado.getVendedores().get(0));
    }

    @Test
    public void testProcessarLinhaDeVendedorVazia() {
        vendedorService.montarObjeto(getLinhaDeVendedorVazia(), dadoProcessado);
        assertTrue(dadoProcessado.getVendedores().isEmpty());
    }

    @Test
    public void testProcessarLinhaDeVendedorComSeparadorDiferente() {
        vendedorService.montarObjeto(getLinhaDeVendedorComOutroSeparador(), dadoProcessado);
        Assert.assertEquals(getVendedor(), dadoProcessado.getVendedores().get(0));
    }

    @Test
    public void testProcessarLinhaDeVendedorContendoCedilhaNoNome() {
        vendedorService.montarObjeto(getLinhaDeVendedorContendoCedilhaNoNome(), dadoProcessado);
        Assert.assertEquals(getVendedorContendoCedilhaNoNome(), dadoProcessado.getVendedores().get(0));
    }

    @Test
    public void testProcessarLinhaDeVendedorComColunasIncompletas() {
        vendedorService.montarObjeto(getLinhaDeVendedorComColunasIncompletas(), dadoProcessado);
        assertTrue(dadoProcessado.getVendedores().isEmpty());
    }

    @Test
    public void testProcessarLinhaDeVendedorComLetraNoSalario() {
        vendedorService.montarObjeto(getLinhaDeVendedorComLetraNoSalario(), dadoProcessado);
        Assert.assertEquals(getVendedor(), dadoProcessado.getVendedores().get(0));
    }


    private String getLinhaDeVendedor() {
        return "001ç1234567891234çDiegoç50000";
    }

    private Vendedor getVendedor() {
        return new Vendedor("1234567891234", "Diego", Double.parseDouble("50000"));
    }

    private String getLinhaDeVendedorVazia() {
        return "";
    }

    private String getLinhaDeVendedorComOutroSeparador() {
        return "001;1234567891234;Diego;50000";
    }

    private String getLinhaDeVendedorContendoCedilhaNoNome() {
        return "001ç1234567891234çAssunçãoç50000";
    }

    private Vendedor getVendedorContendoCedilhaNoNome() {
        return new Vendedor("1234567891234", "Assunção", Double.parseDouble("50000"));
    }

    private String getLinhaDeVendedorComColunasIncompletas() {
        return "001ç1234567891234çç50000";
    }

    private String getLinhaDeVendedorComLetraNoSalario() {
        return "001ç1234567891234çDiegoç50L000Y";
    }
}