package com.matheusvargas481.analisededados.domain;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
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

    public void addVenda(Venda venda) {
        vendas.add(venda);
    }

    public void addCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    public void addVendedor(Vendedor vendedor) {
        vendedores.add(vendedor);
    }

    public List<Venda> getVendas() {
        return vendas;
    }

}
