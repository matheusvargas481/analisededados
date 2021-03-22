//package com.matheusvargas481.analisededados.service;
//
//import com.matheusvargas481.analisededados.domain.Cliente;
//import com.matheusvargas481.analisededados.domain.ItemDeVenda;
//import com.matheusvargas481.analisededados.domain.Venda;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.jupiter.api.Test;
//import org.slf4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.ActiveProfiles;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//
//@SpringBootTest
//@ActiveProfiles("test")
//public class VendaServiceTest {
//
//    @Autowired
//    private VendaService vendaService;
//
//    @MockBean
//    private Logger logger;
//
//    private List<Venda> vendas;
//
//    @Before
//    public void init() {
//        logger.info("startup");
//        vendas = new ArrayList<>();
//    }
//
//    @After
//    public void teardown() {
//        logger.info("teardown");
//        vendas.clear();
//    }
//
//    @Test
//    public void testProcessarLinhasComVendas() {
//        vendas = vendaService.processarLinhasComVendas(getLinhasDoArquivo());
//        assertEquals(getVendas(), vendas);
//    }
//
//    @Test
//    public void testArquivoVazio() {
//        vendas = vendaService.processarLinhasComVendas(getArquivoVazio());
//        assertEquals(Collections.EMPTY_LIST, vendas);
//    }
//
//    @Test
//    public void testArquivoValidoESemLinhasDeVenda() {
//        vendas = vendaService.processarLinhasComVendas(getLinhasDoArquivoSemClientes());
//        assertEquals(Collections.EMPTY_LIST, vendas);
//    }
//
//    @Test
//    public void testComSeparadorDiferente() {
//        vendas = vendaService.processarLinhasComVendas(getLinhasDoArquivoOutroSeparador());
//        assertEquals(getVendas(), vendas);
//    }
//
//    @Test
//    public void testNomeComCedilha() {
//        vendas = vendaService.processarLinhasComVendas(getLinhasDoArquivoContendoNomeComCedilha());
//        assertEquals(getClientesComCedilha(), vendas);
//    }
//
//    @Test
//    public void testColunasIncompletas() {
//        vendas = vendaService.processarLinhasComVendas(getLinhasDoArquivoComCamposIncompletos());
//        assertEquals(getVendasComDadosFaltando(), vendas);
//    }
//
//
//    private List<String> getLinhasDoArquivo() {
//        return Arrays.asList(
//                "001ç1234567891234çDiegoç50000",
//                "001ç3245678865434çRenatoç40000.99",
//                "002ç2345675434544345çJose da SilvaçRural",
//                "002ç2345675433444345çEduardo PereiraçRural",
//                "003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çDiego",
//                "003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çRenato");
//    }
//
//
//    private List<String> getLinhasDoArquivoOutroSeparador() {
//        return Arrays.asList(
//                "001;1234567891234;Diego;50000",
//                "001;3245678865434;Renato;40000.99",
//                "002;2345675434544345;Jose da Silva;Rural",
//                "002;2345675433444345;Eduardo Pereira;Rural",
//                "003;10;[1-10-100,2-30-2.50,3-40-3.10];Diego",
//                "003;08;[1-34-10,2-33-1.50,3-40-0.10];Renato");
//    }
//
//    private List<String> getLinhasDoArquivoComCamposIncompletos() {
//        return Arrays.asList(
//                "002ç2345675434544345ççRural",
//                "002ç2345675437777777çJose da Silvaç",
//                "002ç2345675433444345çEduardo PereiraçRural");
//    }
//
//    private List<String> getLinhasDoArquivoSemClientes() {
//        return Arrays.asList(
//                "001ç1234567891234çDiegoç50000",
//                "001ç3245678865434çRenatoç40000.99",
//                "003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çDiego",
//                "003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çRenato");
//    }
//
//    private List<String> getArquivoVazio() {
//        return Arrays.asList("");
//    }
//
//    private List<Cliente> getVendas() {
//        Cliente clienteEsperadoUm = new Cliente("2345675434544345", "Jose da Silva", "Rural");
//        Cliente clienteEsperadoDois = new Cliente("2345675433444345", "Eduardo Pereira", "Rural");
//        return Arrays.asList(clienteEsperadoUm, clienteEsperadoDois);
//    }
//
//    private List<String> getLinhasDoArquivoContendoNomeComCedilha() {
//        return Arrays.asList(
//                "001ç1234567891234çDiegoç50000",
//                "001ç3245678865434çRenatoç40000.99",
//                "002ç2345675434544345çAssunçãoçRural",
//                "002ç2345675434544345çJose da SilvaçRural",
//                "002ç2345675433444345çConceiçãoçRural",
//                "002ç2345675433444345çEduardo PereiraçRural",
//                "003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çDiego",
//                "003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çRenato");
//    }
//
//    private List<Cliente> getClientesComCedilha() {
//        Cliente clienteEsperadoUm = new Cliente("2345675434544345", "Assunção", "Rural");
//        Cliente clienteEsperadoDois = new Cliente("2345675434544345", "Jose da Silva", "Rural");
//        Cliente clienteEsperadoTres = new Cliente("2345675433444345", "Conceição", "Rural");
//        Cliente clienteEsperadoQuatro = new Cliente("2345675433444345", "Eduardo Pereira", "Rural");
//        return Arrays.asList(clienteEsperadoUm, clienteEsperadoDois, clienteEsperadoTres, clienteEsperadoQuatro);
//    }
//
//    private List<Venda> getVendasComDadosFaltando() {
//        Venda vendaEsperada = new Venda("08", Arrays.asList(new ItemDeVenda() {1,34,10},2-33-1.50,3-40-0.10])"Eduardo Pereira", "Rural");
//        return Arrays.asList(vendaEsperada);
//    }
//}