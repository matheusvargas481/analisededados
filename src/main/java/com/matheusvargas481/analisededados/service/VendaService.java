package com.matheusvargas481.analisededados.service;


import com.matheusvargas481.analisededados.domain.ItemDeVenda;
import com.matheusvargas481.analisededados.domain.Venda;
import com.matheusvargas481.analisededados.domain.Vendedor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class VendaService {
    private List<String> linhasComVendas;
    private List<Venda> vendas;

    public List<Venda> getVendas() {
        return vendas;
    }

    public List<String> getLinhasComVendas() {
        return linhasComVendas;
    }

    public void identificarVendas(List<String> vendasDoArquivo) {
        linhasComVendas = new ArrayList<>();
        for (String venda : vendasDoArquivo) {
            if (venda.startsWith(Venda.COMECA_COM_003)) {
                linhasComVendas.add(venda);
            }
        }
        montarVenda(linhasComVendas);
    }

    public Long buscarIdDaVendaDeMaiorValor() {
        return vendas.stream().max(Comparator.comparing(Venda::valorTotalDaVenda)).map(Venda::getId).get();
    }

    public String buscarPiorVendedor() {
        return vendas.stream().min(Comparator.comparing(Venda::valorTotalDaVenda)).map(Vendedor::new).get().getNome();
    }

    private void montarVenda(List<String> linhasComVendas) {
        vendas = new ArrayList<>();
        for (String linhasDeVendas : linhasComVendas) {
            String separador = linhasDeVendas.substring(3, 4);
            String[] linhasDeVendasSemSeparador = linhasDeVendas.split(separador);
            try {
                Venda venda = new Venda();
                venda.setId(Long.parseLong(linhasDeVendasSemSeparador[1]));
                venda.setItensDeVendas(montaListaDeItemDeVenda(linhasDeVendasSemSeparador[2]));
                venda.setNome(linhasDeVendasSemSeparador[3]);
                vendas.add(venda);
            } catch (RuntimeException e) {
                //TODO tratar vendas que contém erros
                throw new RuntimeException("Não foi possível montar a venda.", e.getCause());
            }
        }
    }

    private List<ItemDeVenda> montaListaDeItemDeVenda(String itensDaVenda) {
        List<ItemDeVenda> itensDeVendas = new ArrayList<>();
        String[] listaDeItens = itensDaVenda
                .replace("[", "")
                .replace("]", "")
                .split(",");

        for (int indiceListaDeItens = 0; indiceListaDeItens < listaDeItens.length; indiceListaDeItens++) {
            String[] item = listaDeItens[indiceListaDeItens].split("-");
            ItemDeVenda itemDeVenda = new ItemDeVenda();
            itemDeVenda.setId(Long.parseLong(item[0]));
            itemDeVenda.setQuantidade(Integer.parseInt(item[1]));
            itemDeVenda.setPreco(Double.parseDouble(item[2]));
            itensDeVendas.add(itemDeVenda);
        }

        return itensDeVendas;
    }
}
