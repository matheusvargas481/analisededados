package com.matheusvargas481.analisededados.service;


import com.matheusvargas481.analisededados.domain.DadoProcessado;
import com.matheusvargas481.analisededados.domain.ItemDeVenda;
import com.matheusvargas481.analisededados.domain.Venda;
import com.matheusvargas481.analisededados.exception.LayoutDeVendaDiferenteDoEsperadoException;
import com.matheusvargas481.analisededados.exception.LayoutDoItemDeVendaDiferenteDoEsperadoException;
import com.matheusvargas481.analisededados.strategy.MontaObjetoStrategy;
import com.matheusvargas481.analisededados.util.Separador;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service(value = "003")
public class VendaService extends Separador implements MontaObjetoStrategy {
    private static Logger LOGGER = LoggerFactory.getLogger(VendaService.class);

    private static final String MENSAGEM_DE_ERRO_NO_LAYOUT_DA_VENDA = "Não foi possível montar o venda: {} pelo motivo: {}";
    private static final String MENSAGEM_DE_ERRO_NO_LAYOUT_DO_ITEM_DE_VENDA = "Não foi possível montar o item de venda: {} pelo motivo: {}";
    private static final String MENSAGEM_DE_ERRO_NO_PARSE_DO_ITEM_DE_VENDA = "Não foi possível efetuar o parse do item de venda: {} por alguma inconsistência no layout.  Motivo: {}";

    @Override
    public void montarObjeto(String linhaDeVenda, DadoProcessado dadoProcessado) {
        if (!linhaDeVenda.isEmpty()) {
            if (isLinhaDeVendaValido(linhaDeVenda)) {
                String[] linhasDeVendasSemSeparador = separarLinhaParaMontarObjeto(linhaDeVenda);

                try {
                    if (linhasDeVendasSemSeparador.length == 4) {
                        Venda venda = new Venda();
                        venda.setId(Long.parseLong(linhasDeVendasSemSeparador[1]));
                        venda.setItensDeVendas(montaListaDeItemDeVenda(linhasDeVendasSemSeparador[2]));
                        venda.setNome(linhasDeVendasSemSeparador[3]);
                        dadoProcessado.addVenda(venda);
                    } else
                        throw new LayoutDeVendaDiferenteDoEsperadoException();
                } catch (LayoutDeVendaDiferenteDoEsperadoException e) {
                    LOGGER.error(MENSAGEM_DE_ERRO_NO_LAYOUT_DA_VENDA, linhaDeVenda, e.getCause());
                }
            }
        }

    }

    private List<ItemDeVenda> montaListaDeItemDeVenda(String itensDaVenda) {
        List<ItemDeVenda> itensDeVendas = new ArrayList<>();

        String[] listaDeItens = itensDaVenda
                .replace("[", "")
                .replace("]", "")
                .split(",");

        for (int indiceItensDeVenda = 0; indiceItensDeVenda < listaDeItens.length; indiceItensDeVenda++) {
            String[] itemDeVendaSeparado = listaDeItens[indiceItensDeVenda].split("-");

            try {
                if (itemDeVendaSeparado.length == 3 && listaDeItens.length == 3) {
                    ItemDeVenda itemDeVenda = new ItemDeVenda();
                    itemDeVenda.setId(Long.parseLong(itemDeVendaSeparado[0]));
                    itemDeVenda.setQuantidade(Integer.parseInt(itemDeVendaSeparado[1]));
                    itemDeVenda.setPreco(Double.parseDouble(itemDeVendaSeparado[2]));
                    itensDeVendas.add(itemDeVenda);
                } else {
                    throw new LayoutDoItemDeVendaDiferenteDoEsperadoException();
                }
            } catch (LayoutDoItemDeVendaDiferenteDoEsperadoException e) {
                LOGGER.error(MENSAGEM_DE_ERRO_NO_LAYOUT_DO_ITEM_DE_VENDA, itemDeVendaSeparado, e.getCause());
            } catch (NumberFormatException n) {
                LOGGER.error(MENSAGEM_DE_ERRO_NO_PARSE_DO_ITEM_DE_VENDA, itemDeVendaSeparado, n.getCause());
            }
        }
        return itensDeVendas;
    }

    private boolean isLinhaDeVendaValido(String linhaComCliente) {
        return linhaComCliente.startsWith(Venda.COMECA_COM_003);
    }

}
