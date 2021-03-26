package com.matheusvargas481.analisededados.domain;

import java.util.function.Consumer;

public class ItemDeVenda {
    private Long id;
    private int quantidade;
    private Double preco;

    public ItemDeVenda(Consumer<ItemDeVenda> itemDeVenda) {
        itemDeVenda.accept(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    @Override
    public String toString() {
        return "ItemDeVenda{" +
                "id=" + id +
                ", quantidade=" + quantidade +
                ", preco=" + preco +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemDeVenda that = (ItemDeVenda) o;
        return quantidade == that.quantidade &&
                id.equals(that.id) &&
                preco.equals(that.preco);
    }
}
