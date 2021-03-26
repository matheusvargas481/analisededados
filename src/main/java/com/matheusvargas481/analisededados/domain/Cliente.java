package com.matheusvargas481.analisededados.domain;

import java.util.Objects;
import java.util.function.Consumer;

public class Cliente {
    private String cnpj;
    private String nome;
    private String areaDeNegocio;

    public Cliente(Consumer<Cliente> cliente) {
        cliente.accept(this);
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAreaDeNegocio() {
        return areaDeNegocio;
    }

    public void setAreaDeNegocio(String areaDeNegocio) {
        this.areaDeNegocio = areaDeNegocio;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "cnpj='" + cnpj + '\'' +
                ", nome='" + nome + '\'' +
                ", areaDeNegocio='" + areaDeNegocio + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(cnpj, cliente.cnpj) && Objects.equals(nome, cliente.nome) && Objects.equals(areaDeNegocio, cliente.areaDeNegocio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cnpj, nome, areaDeNegocio);
    }
}
