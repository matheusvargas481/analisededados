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
        vendedorService.processarLinha(getLinhaDeVendedor(), dadoProcessado);
        Assert.assertEquals(getVendedor(), dadoProcessado.getVendedores().get(0));
    }

    @Test
    public void testProcessarLinhaDeVendedorComSeparadorDiferente() {
        vendedorService.processarLinha(getLinhaDeVendedorComOutroSeparador(), dadoProcessado);
        Assert.assertEquals(getVendedor(), dadoProcessado.getVendedores().get(0));
    }

    @Test
    public void testProcessarLinhaDeVendedorContendoCedilhaNoNome() {
        vendedorService.processarLinha(getLinhaDeVendedorContendoCedilhaNoNome(), dadoProcessado);
        Assert.assertEquals(getVendedorContendoCedilhaNoNome(), dadoProcessado.getVendedores().get(0));
    }

    @Test
    public void testProcessarLinhaDeVendedorComColunasIncompletas() {
        vendedorService.processarLinha(getLinhaDeVendedorComColunasIncompletas(), dadoProcessado);
        assertTrue(dadoProcessado.getVendedores().isEmpty());
    }

    @Test
    public void testProcessarLinhaDeVendedorComLetraNoSalario() {
        vendedorService.processarLinha(getLinhaDeVendedorComLetraNoSalario(), dadoProcessado);
        Assert.assertEquals(getVendedor(), dadoProcessado.getVendedores().get(0));
    }


    private String getLinhaDeVendedor() {
        return "001ç1234567891234çDiegoç50000";
    }

    private Vendedor getVendedor() {
        return new Vendedor($ -> {
            $.setCpf("1234567891234");
            $.setNome("Diego");
            $.setSalario(Double.parseDouble("50000"));
        });
    }

    private String getLinhaDeVendedorComOutroSeparador() {
        return "001;1234567891234;Diego;50000";
    }

    private String getLinhaDeVendedorContendoCedilhaNoNome() {
        return "001ç1234567891234çAssunçãoç50000";
    }

    private Vendedor getVendedorContendoCedilhaNoNome() {
        return new Vendedor($ -> {
            $.setCpf("1234567891234");
            $.setNome("Assunção");
            $.setSalario(Double.parseDouble("50000"));
        });
    }

    private String getLinhaDeVendedorComColunasIncompletas() {
        return "001ç1234567891234çç50000";
    }

    private String getLinhaDeVendedorComLetraNoSalario() {
        return "001ç1234567891234çDiegoç50L000Y";
    }
}