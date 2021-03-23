package com.matheusvargas481.analisededados.domain;

import org.springframework.stereotype.Component;

@Component
public class DadoFinalParaEscritaNoArquivo {
    private int quantidadeDeCliente;
    private int quantidadeDeVendedor;
    private Long idDaVendaDeMaiorValor;
    private String nomePiorVendedor;

    public int getQuantidadeDeCliente() {
        return quantidadeDeCliente;
    }

    public void setQuantidadeDeCliente(int quantidadeDeCliente) {
        this.quantidadeDeCliente = quantidadeDeCliente;
    }

    public int getQuantidadeDeVendedor() {
        return quantidadeDeVendedor;
    }

    public void setQuantidadeDeVendedor(int quantidadeDeVendedor) {
        this.quantidadeDeVendedor = quantidadeDeVendedor;
    }

    public Long getIdDaVendaDeMaiorValor() {
        return idDaVendaDeMaiorValor;
    }

    public void setIdDaVendaDeMaiorValor(Long idDaVendaDeMaiorValor) {
        this.idDaVendaDeMaiorValor = idDaVendaDeMaiorValor;
    }

    public String getNomePiorVendedor() {
        return nomePiorVendedor;
    }

    public void setNomePiorVendedor(String nomePiorVendedor) {
        this.nomePiorVendedor = nomePiorVendedor;
    }

    @Override
    public String toString() {
        return "DadoFinalParaEscritaNoArquivo{" +
                "quantidadeDeCliente=" + quantidadeDeCliente +
                ", quantidadeDeVendedor=" + quantidadeDeVendedor +
                ", idDaVendaDeMaiorValor=" + idDaVendaDeMaiorValor +
                ", nomePiorVendedor='" + nomePiorVendedor + '\'' +
                '}';
    }
}
