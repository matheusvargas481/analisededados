package com.matheusvargas481.analisededados.service;


import com.matheusvargas481.analisededados.domain.ItemDeVenda;
import com.matheusvargas481.analisededados.domain.Venda;
import com.matheusvargas481.analisededados.exception.ErroAoMontarItemDeVendaException;
import com.matheusvargas481.analisededados.exception.ErroAoMontarVendaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class VendaService {
    private static Logger log = LoggerFactory.getLogger(VendaService.class);

    public List<Venda> processarLinhasComVendas(List<String> linhasDoArquivo) {
        return linhasDoArquivo
                .stream()
                .filter(this::isLinhaValidaVenda)
                .map(this::montarVenda)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private boolean isLinhaValidaVenda(String venda) {
        return venda.startsWith(Venda.COMECA_COM_003);
    }

    private Venda montarVenda(String linhaComVenda) {
        String separador = linhaComVenda.substring(3, 4);
        if (separador.equalsIgnoreCase("ç"))
            separador = "ç(?![a-zç])";
        String[] linhasDeVendasSemSeparador = linhaComVenda.split(separador);

        try {
            if (linhasDeVendasSemSeparador.length != 4) throw new ErroAoMontarVendaException();
            Venda venda = new Venda();
            venda.setId(Long.parseLong(linhasDeVendasSemSeparador[1]));
            venda.setItensDeVendas(montaListaDeItemDeVenda(linhasDeVendasSemSeparador[2]));
            venda.setNome(linhasDeVendasSemSeparador[3]);
            return venda;
        } catch (ErroAoMontarVendaException e) {
            log.error("Não foi possível montar o venda: " + linhaComVenda + " pelo motivo: " + e.getCause());
            return null;
        }
    }

    private List<ItemDeVenda> montaListaDeItemDeVenda(String itensDaVenda) {
        List<ItemDeVenda> itensDeVendas = new ArrayList<>();

        String[] listaDeItens = itensDaVenda
                .replace("[", "")
                .replace("]", "")
                .split(",");

        if (listaDeItens.length != 3) throw new ErroAoMontarItemDeVendaException();
        for (int indiceListaDeItens = 0; indiceListaDeItens < listaDeItens.length; indiceListaDeItens++) {
            String[] itemDeVendaSplit = listaDeItens[indiceListaDeItens].split("-");
            try {
                ItemDeVenda itemDeVenda = new ItemDeVenda();
                itemDeVenda.setId(Long.parseLong(itemDeVendaSplit[0]));
                itemDeVenda.setQuantidade(Integer.parseInt(itemDeVendaSplit[1]));
                itemDeVenda.setPreco(Double.parseDouble(itemDeVendaSplit[2]));
                itensDeVendas.add(itemDeVenda);
            } catch (ErroAoMontarItemDeVendaException e) {
                log.error("Não foi possível montar o item de venda: " + itemDeVendaSplit + " pelo motivo: " + e.getCause());
                return null;
            }
        }
        return itensDeVendas;
    }
}
