package com.matheusvargas481.analisededados.service;

import com.matheusvargas481.analisededados.domain.Vendedor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class VendedorServiceTest {

    @Autowired
    private VendedorService vendedorService;

    @Test
    public void testIdentificarVendedor() {
        vendedorService.identificarVendedores(getLinhasDoArquivo());
        Assertions.assertEquals(getLinhasDeCliente(), vendedorService.getLinhasComVendedores());
    }

    @Test
    public void testMontarVendedor() {
        vendedorService.identificarVendedores(getLinhasDoArquivo());
        Assertions.assertEquals(getVendedores(), vendedorService.getVendedores());
    }

    @Test
    public void testQuantidadeDeVendedoresNoArquivo() {
        vendedorService.identificarVendedores(getLinhasDoArquivo());
        Assertions.assertEquals(2, vendedorService.buscarQuantidadeDeVendedores());
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

    private List<String> getLinhasDeCliente() {
        return Arrays.asList(
                "001ç1234567891234çDiegoç50000",
                "001ç3245678865434çRenatoç40000.99");
    }

    private List<Vendedor> getVendedores() {
        Vendedor vendedorEsperadoUm = new Vendedor("1234567891234", "Diego", 50000);
        Vendedor vendedorEsperadoDois = new Vendedor("3245678865434", "Renato", 40000.99);
        return Arrays.asList(vendedorEsperadoUm, vendedorEsperadoDois);
    }

}