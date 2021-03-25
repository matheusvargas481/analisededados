package com.matheusvargas481.analisededados.domain;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DadoProcessado {

    private List<Cliente> clientes = new ArrayList<>();
    private List<Vendedor> vendedores = new ArrayList<>();
    private List<Venda> vendas = new ArrayList<>();

    public DadoProcessado addVenda(Venda venda) {
        this.vendas.add(venda);
        return this;
    }

    public DadoProcessado addCliente(Cliente cliente) {
        this.clientes.add(cliente);
        return this;
    }

    public DadoProcessado addVendedor(Vendedor vendedor) {
        this.vendedores.add(vendedor);
        return this;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public List<Vendedor> getVendedores() {
        return vendedores;
    }

    public List<Venda> getVendas() {
        return vendas;
    }


    public int buscarQuantidadeDeClientes() {
        if (clientes.isEmpty()) return 0;
        return clientes.size();
    }

    public int buscarQuantidadeDeVendedores() {
        if (vendedores.isEmpty()) return 0;
        return vendedores.size();
    }

    public Long buscarIdDaVendaDeMaiorValor() {
        if (vendas.isEmpty()) return 0L;
        return vendas.stream().max(Comparator.comparing(Venda::valorTotalDaVenda)).map(Venda::getId).get();
    }

    public String buscarPiorVendedor() {
        if (vendas.isEmpty()) return "";
        return vendas.stream().min(Comparator.comparing(Venda::valorTotalDaVenda)).map(Venda::getNome).get();
    }

}
