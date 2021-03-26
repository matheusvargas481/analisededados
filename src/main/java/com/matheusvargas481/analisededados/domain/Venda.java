package com.matheusvargas481.analisededados.domain;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class Venda {
    private Long id;
    private List<ItemDeVenda> itensDeVendas;
    private String nome;

    public Venda() {
    }

    public Venda(Consumer<Venda> venda) {
        venda.accept(this);
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

    public Double valorTotalDaVenda() {
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
        return Objects.equals(id, venda.id) && Objects.equals(itensDeVendas, venda.itensDeVendas) && Objects.equals(nome, venda.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, itensDeVendas, nome);
    }
}
