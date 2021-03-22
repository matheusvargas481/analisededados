package com.matheusvargas481.analisededados.domain;

import java.util.List;

public class Venda {
    public static final String COMECA_COM_003 = "003";
    private Long id;
    private List<ItemDeVenda> itensDeVendas;
    private String nome;

    public Venda() {
    }

    public Venda(Long id, List<ItemDeVenda> itensDeVendas, String nome) {
        this.id = id;
        this.itensDeVendas = itensDeVendas;
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ItemDeVenda> getItensDeVendas() {
        return itensDeVendas;
    }

    public void setItensDeVendas(List<ItemDeVenda> itensDeVendas) {
        this.itensDeVendas = itensDeVendas;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double valorTotalDaVenda() {
        return itensDeVendas.stream().mapToDouble(itemDeVenda -> itemDeVenda.getQuantidade() * itemDeVenda.getPreco()).sum();
    }

    @Override
    public String toString() {
        return "\n" + "Venda{ Id: " + id + " ,itensDeVendas: " + itensDeVendas + ", nome: " + nome + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Venda venda = (Venda) o;
        return id.equals(venda.id) &&
                itensDeVendas.equals(venda.itensDeVendas) &&
                nome.equals(venda.nome);
    }
}
