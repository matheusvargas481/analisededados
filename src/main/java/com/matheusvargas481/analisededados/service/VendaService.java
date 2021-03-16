package com.matheusvargas481.analisededados.service;


import com.matheusvargas481.analisededados.domain.DadoProcessado;
import com.matheusvargas481.analisededados.domain.ItemDeVenda;
import com.matheusvargas481.analisededados.domain.Venda;
import com.matheusvargas481.analisededados.exception.ErroAoMontarItemDeVendaException;
import com.matheusvargas481.analisededados.exception.ErroAoMontarVendaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class VendaService {
    private static Logger log = LoggerFactory.getLogger(VendaService.class);

    @Autowired
    private DadoProcessado dadoProcessado;

    public void processarLinhasComVendas(List<String> linhasDoArquivo) {
        linhasDoArquivo
                .stream()
                .filter(this::isLinhaValidaVenda)
                .forEach(this::montarVenda);
    }

    private boolean isLinhaValidaVenda(String venda) {
        return venda.startsWith(Venda.COMECA_COM_003);
    }

    public Long buscarIdDaVendaDeMaiorValor() {
        if (dadoProcessado.getVendas().isEmpty()) return 0L;
        return dadoProcessado.getVendas().stream().max(Comparator.comparing(Venda::valorTotalDaVenda)).map(Venda::getId).get();
    }

    public String buscarPiorVendedor() {
        if (dadoProcessado.getVendas().isEmpty()) return "";
        return dadoProcessado.getVendas().stream().min(Comparator.comparing(Venda::valorTotalDaVenda)).map(Venda::getNome).get();
    }

    private void montarVenda(String linhaComVenda) {
        //String separador = linhaComVenda.substring(3, 4);
        String[] linhasDeVendasSemSeparador = linhaComVenda.split("/ç(?![a-zç])/");

        try {
            if (linhasDeVendasSemSeparador.length != 4) throw new ErroAoMontarVendaException();
            Venda venda = new Venda();
            venda.setId(Long.parseLong(linhasDeVendasSemSeparador[1]));
            venda.setItensDeVendas(montaListaDeItemDeVenda(linhasDeVendasSemSeparador[2]));
            venda.setNome(linhasDeVendasSemSeparador[3]);
            dadoProcessado.addVenda(venda);
        } catch (ErroAoMontarVendaException e) {
            log.error("Não foi possível montar o venda: " + linhaComVenda + " pelo motivo: " + e.getCause());
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
