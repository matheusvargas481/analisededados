package com.matheusvargas481.analisededados.service;


import com.matheusvargas481.analisededados.domain.ItemDeVenda;
import com.matheusvargas481.analisededados.domain.Venda;
import com.matheusvargas481.analisededados.exception.ErroAoMontarItemDeVendaException;
import com.matheusvargas481.analisededados.exception.ErroAoMontarVendaException;
import com.matheusvargas481.analisededados.util.Separador;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class VendaService extends Separador {
    private static Logger LOGGER = LoggerFactory.getLogger(VendaService.class);

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

        String[] linhasDeVendasSemSeparador = separarLinhaParaMontarObjeto(linhaComVenda);

        try {
            if (linhasDeVendasSemSeparador.length != 4) throw new ErroAoMontarVendaException();

            return new Venda(
                    Long.parseLong(linhasDeVendasSemSeparador[1]),
                    montaListaDeItemDeVenda(linhasDeVendasSemSeparador[2]),
                    linhasDeVendasSemSeparador[3]
            );

        } catch (ErroAoMontarVendaException e) {
            LOGGER.error("Não foi possível montar o venda: " + linhaComVenda + " pelo motivo: " + e.getCause());
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

        for (int indiceItensDeVenda = 0; indiceItensDeVenda < listaDeItens.length; indiceItensDeVenda++) {
            String[] itemDeVendaSeparado = listaDeItens[indiceItensDeVenda].split("-");
            try {
                ItemDeVenda itemDeVenda = new ItemDeVenda();
                itemDeVenda.setId(Long.parseLong(itemDeVendaSeparado[0]));
                itemDeVenda.setQuantidade(Integer.parseInt(itemDeVendaSeparado[1]));
                itemDeVenda.setPreco(Double.parseDouble(itemDeVendaSeparado[2]));
                itensDeVendas.add(itemDeVenda);
            } catch (ErroAoMontarItemDeVendaException e) {
                LOGGER.error("Não foi possível montar o item de venda: " + itemDeVendaSeparado + " pelo motivo: " + e.getCause());
                return null;
            }
        }
        return itensDeVendas;
    }

}
