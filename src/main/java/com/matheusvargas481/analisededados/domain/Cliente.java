package com.matheusvargas481.analisededados.domain;

public class Cliente {
    public static final String COMECA_COM_002 = "002";
    private String cnpj;
    private String nome;
    private String areaDeNegocio;

    public Cliente() {
    }

    public Cliente(String cnpj, String nome, String areaDeNegocio) {
        this.cnpj = cnpj;
        this.nome = nome;
        this.areaDeNegocio = areaDeNegocio;
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
        return cnpj.equals(cliente.cnpj) &&
                nome.equals(cliente.nome) &&
                areaDeNegocio.equals(cliente.areaDeNegocio);
    }
}
