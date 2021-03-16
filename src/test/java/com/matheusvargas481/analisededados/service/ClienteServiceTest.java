//package com.matheusvargas481.analisededados.service;
//
//import com.matheusvargas481.analisededados.domain.Cliente;
//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.slf4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//public class ClienteServiceTest {
//    @Autowired
//    private ClienteService clienteService;
//
//    @MockBean
//    private Logger logger;
//
//
//    @Test
//    public void testFiltrarCliente() {
//        List<Cliente> clientes = clienteService.filtrarClientes(getLinhasDoArquivo());
//        assertEquals(getClientes(), clientes);
//    }
//
//    @Test
//    public void testMontarCliente() {
//        List<Cliente> clientes = clienteService.filtrarClientes(getLinhasDoArquivo());
//        assertEquals(getClientes(), clientes);
//    }
//
//    @Test
//    public void testQuantidadeDeClientesNoArquivo() {
//        List<Cliente> clientes = clienteService.filtrarClientes(getLinhasDoArquivo());
//        assertEquals(clientes.size(), clienteService.buscarQuantidadeDeClientes());
//    }
//
//    @Test
//    public void testArquivoVazio(){
//        List<Cliente> clientes = clienteService.filtrarClientes(getArquivoVazio());
//        assertEquals(Collections.EMPTY_LIST, clientes);
//    }
//
//    @Test
//    public void testArquivoValidoESemLinhasDeCliente(){
//        List<Cliente> clientes = clienteService.filtrarClientes(getLinhasDoArquivoSemClientes());
//        assertEquals(Collections.EMPTY_LIST, clientes);
//    }
//
////    //TODO REVER
////    @Test
////    public void testColunasIncompletas(){
////        List<Cliente> clientes = clienteService.filtrarClientes(getLinhasDoArquivoComCamposIncompletos());
////       // assertEquals(Collections.EMPTY_LIST, clientes);
////
////    }
//
//    @Test
//    public void testComSeparadorDiferente(){
//        List<Cliente> clientes = clienteService.filtrarClientes(getLinhasDoArquivoOutroSeparador());
//        assertEquals(getClientes(), clientes);
//    }
//
////    @Test
////    public void testNomeComCedilha(){
////        List<Cliente> clientes = clienteService.filtrarClientes(getLinhasDoArquivoContendoNomeComCedilha());
////        assertEquals(getClientes(), clientes);
////    }
//
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
//                "002ççEduardo PereiraçRural");
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
//        return Arrays.asList(
//                "",
//                "",
//                "",
//                "",
//                "",
//                "");
//    }
//
//    private List<String> getLinhasDeCliente() {
//        return Arrays.asList(
//                "002ç2345675434544345çJose da SilvaçRural",
//                "002ç2345675433444345çEduardo PereiraçRural");
//    }
//
//    private List<Cliente> getClientes() {
//        Cliente clienteEsperadoUm = new Cliente("2345675434544345", "Jose da Silva", "Rural");
//        Cliente clienteEsperadoDois = new Cliente("2345675433444345", "Eduardo Pereira", "Rural");
//        return Arrays.asList(clienteEsperadoUm, clienteEsperadoDois);
//    }
//
//}
