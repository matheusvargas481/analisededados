package com.matheusvargas481.analisededados.domain;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class DadoProcessado {
    private List<Cliente> clientes = new ArrayList<>();
    private List<Vendedor> vendedores = new ArrayList<>();
    private List<Venda> vendas = new ArrayList<>();

    public void limparListas() {
        clientes = new ArrayList<>();
        vendedores = new ArrayList<>();
        vendas = new ArrayList<>();
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public List<Vendedor> getVendedores() {
        return vendedores;
    }

    public void setVendedores(List<Vendedor> vendedores) {
        this.vendedores = vendedores;
    }

    public List<Venda> getVendas() {
        return vendas;
    }

    public void setVendas(List<Venda> vendas) {
        this.vendas = vendas;
    }

    public int buscarQuantidadeDeClientes() {
        return clientes.size();
    }

    public Long buscarIdDaVendaDeMaiorValor() {
        if (vendas.isEmpty()) return 0L;
        return vendas.stream().max(Comparator.comparing(Venda::valorTotalDaVenda)).map(Venda::getId).get();
    }

    public String buscarPiorVendedor() {
        if (vendas.isEmpty()) return "";
        return vendas.stream().min(Comparator.comparing(Venda::valorTotalDaVenda)).map(Venda::getNome).get();
    }

    public int buscarQuantidadeDeVendedores() {
        return vendedores.size();
    }
}
