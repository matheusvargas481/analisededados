package com.matheusvargas481.analisededados.service;

import com.matheusvargas481.analisededados.domain.Vendedor;
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
public class VendedorServiceTest {

    @Autowired
    private VendedorService vendedorService;

    @MockBean
    private Logger logger;

    private List<Vendedor> vendedores;

    @Before
    public void init() {
        logger.info("startup");
        vendedores = new ArrayList<>();
    }

    @After
    public void teardown() {
        logger.info("teardown");
        vendedores.clear();
    }

    @Test
    public void testProcessarLinhasComVendedores() {
        vendedores = vendedorService.processarLinhasComVendedores(getLinhasDoArquivo());
        assertEquals(getVendedores(), vendedores);
    }

    @Test
    public void testArquivoVazio() {
        vendedores = vendedorService.processarLinhasComVendedores(getArquivoVazio());
        assertEquals(Collections.EMPTY_LIST, vendedores);
    }

    @Test
    public void testArquivoValidoESemLinhasDeVendedor() {
        vendedores = vendedorService.processarLinhasComVendedores(getLinhasDoArquivoSemVendedores());
        assertEquals(Collections.EMPTY_LIST, vendedores);
    }

    @Test
    public void testComSeparadorDiferente() {
        vendedores = vendedorService.processarLinhasComVendedores(getLinhasDeVendedorComOutroSeparador());
        assertEquals(getVendedores(), vendedores);
    }

    @Test
    public void testNomeComCedilha() {
        vendedores = vendedorService.processarLinhasComVendedores(getLinhasDeVendedoresContendoCedilhaNoNome());
        assertEquals(getVendedoresComCedilha(), vendedores);
    }

    @Test
    public void testColunasIncompletas() {
        vendedores = vendedorService.processarLinhasComVendedores(getLinhasDeVendedorComCamposIncompletos());
        assertEquals(getVendedoresComDadosFaltando(), vendedores);
    }

    //TODO REVER
//    @Test
//    public void testSalarioDoVendedorContendoLetras() {
//        vendedores = vendedorService.processarLinhasComVendedores(getLinhasDeVendedoresComLetrasNoSalario());
//        assertEquals(Collections.EMPTY_LIST, vendedores);
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

    private List<String> getLinhasDeVendedoresComLetrasNoSalario() {
        return Arrays.asList(
                "001ç1234567891234çDiegoç50000Y",
                "001ç3245678865434çRenatoç40000.9D");
    }


    private List<String> getLinhasDeVendedorComOutroSeparador() {
        return Arrays.asList(
                "001;1234567891234;Diego;50000",
                "001;3245678865434;Renato;40000.99");
    }

    private List<String> getLinhasDeVendedorComCamposIncompletos() {
        return Arrays.asList(
                "001ççDiegoç50000",
                "001ç3245678865434çRenatoç",
                "001ç1234567891234çJuniorç50000");
    }

    private List<String> getLinhasDoArquivoSemVendedores() {
        return Arrays.asList(
                "002ç2345675434544345çJose da SilvaçRural",
                "002ç2345675433444345çEduardo PereiraçRural",
                "003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çDiego",
                "003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çRenato");
    }

    private List<String> getArquivoVazio() {
        return Arrays.asList("");
    }

    private List<Vendedor> getVendedores() {
        Vendedor vendedorEsperadoUm = new Vendedor("1234567891234", "Diego", Double.parseDouble("50000"));
        Vendedor vendedorEsperadoDois = new Vendedor("3245678865434", "Renato", Double.parseDouble("40000.99"));
        return Arrays.asList(vendedorEsperadoUm, vendedorEsperadoDois);
    }

    private List<String> getLinhasDeVendedoresContendoCedilhaNoNome() {
        return Arrays.asList(
                "001ç1234567891234çAssunçãoç50000",
                "001ç3245678865434çConceiçãoç40000.99");
    }

    private List<Vendedor> getVendedoresComCedilha() {
        Vendedor vendedorEsperadoUm = new Vendedor("1234567891234", "Assunção", Double.parseDouble("50000"));
        Vendedor vendedorEsperadoDois = new Vendedor("3245678865434", "Conceição", Double.parseDouble("40000.99"));
        return Arrays.asList(vendedorEsperadoUm, vendedorEsperadoDois);
    }

    private List<Vendedor> getVendedoresComDadosFaltando() {
        Vendedor vendedorEsperado = new Vendedor("1234567891234", "Junior", Double.parseDouble("50000"));
        return Arrays.asList(vendedorEsperado);
    }
}