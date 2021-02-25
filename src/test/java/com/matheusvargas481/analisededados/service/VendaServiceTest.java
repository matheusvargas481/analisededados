package com.matheusvargas481.analisededados.service;

import com.matheusvargas481.analisededados.domain.Cliente;
import com.matheusvargas481.analisededados.domain.ItemDeVenda;
import com.matheusvargas481.analisededados.domain.Venda;
import com.matheusvargas481.analisededados.domain.Vendedor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class VendaServiceTest {
    @Autowired
    private VendaService vendaService;

    @Test
    public void testIdentificarVenda() {
        vendaService.identificarVendas(getLinhasDoArquivo());
        assertEquals(getLinhasDeVenda(), vendaService.getLinhasComVendas());
    }

    @Test
    public void testMontarVenda() {
        vendaService.identificarVendas(getLinhasDoArquivo());
        assertEquals(getVendas(), vendaService.getVendas());
    }

    @Test
    public void testBuscarIdDaVendaDeMaiorValor(){
        vendaService.identificarVendas(getLinhasDoArquivo());
        Long idDaVendaDeMaiorValor = vendaService.buscarIdDaVendaDeMaiorValor();
        Long idEsperado = getVendas().get(0).getId();
        assertEquals(idEsperado, idDaVendaDeMaiorValor);
    }

    @Test
    public void testBuscarPiorVendedor(){
        vendaService.identificarVendas(getLinhasDoArquivo());
        String vendedor = vendaService.buscarPiorVendedor();
        String vendedorEsperado = getVendas().get(1).getNome();
        assertEquals(vendedorEsperado, vendedor);
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

    private List<String> getLinhasDeVenda() {
        return Arrays.asList(
                "003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çDiego",
                "003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çRenato");
    }

    private List<Venda> getVendas() {
        List<ItemDeVenda> itemDeVendasEsperadoUm = Arrays.asList(new ItemDeVenda(1L,10,100.0),
                new ItemDeVenda(2L,30,2.50),
                new ItemDeVenda(3L,40,3.10));
        List<ItemDeVenda> itemDeVendasEsperadoDois = Arrays.asList(new ItemDeVenda(1L,34,10.0),
                new ItemDeVenda(2L,33,1.50),
                new ItemDeVenda(3L,40,0.10));

        Venda vendaEsperadaUm = new Venda(10L, itemDeVendasEsperadoUm,"Diego");
        Venda vendaEsperadaDois = new Venda(8L, itemDeVendasEsperadoDois,"Renato");

        return Arrays.asList(vendaEsperadaUm, vendaEsperadaDois);
    }
}