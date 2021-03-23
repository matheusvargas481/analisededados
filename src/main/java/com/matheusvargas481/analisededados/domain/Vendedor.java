package com.matheusvargas481.analisededados.domain;

import java.util.Objects;

public class Vendedor {
    public static final String COMECA_COM_001 = "001";
    private String cpf;
    private String nome;
    private double salario;

    public Vendedor() {
    }

    public Vendedor(String cpf, String nome, double salario) {
        this.cpf = cpf;
        this.nome = nome;
        this.salario = salario;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    @Override
    public String toString() {
        return "Vendedor{" +
                "cpf='" + cpf + '\'' +
                ", nome='" + nome + '\'' +
                ", salario=" + salario +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vendedor vendedor = (Vendedor) o;
        return Double.compare(vendedor.salario, salario) == 0 && Objects.equals(cpf, vendedor.cpf) && Objects.equals(nome, vendedor.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpf, nome, salario);
    }
}
