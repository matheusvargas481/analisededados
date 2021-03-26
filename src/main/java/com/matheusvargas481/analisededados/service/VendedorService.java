package com.matheusvargas481.analisededados.service;

import com.matheusvargas481.analisededados.domain.DadoProcessado;
import com.matheusvargas481.analisededados.domain.Vendedor;
import com.matheusvargas481.analisededados.exception.LayoutDeVendedorDiferenteDoEsperadoException;
import com.matheusvargas481.analisededados.strategy.ProcessaLinhaStrategy;
import com.matheusvargas481.analisededados.util.Separador;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service(value = "001")
public class VendedorService extends Separador implements ProcessaLinhaStrategy {
    private static final String REGEX_RETIRA_LETRAS_CAMPO_SALARIO = "[^\\d\\.]+";
    private static Logger LOGGER = LoggerFactory.getLogger(VendedorService.class);
    private static final String MENSAGEM_DE_ERRO_NO_LAYOUT_DO_VENDEDOR = "Não foi possível montar o vendedor: {} pelo motivo: {}";
    private static final String MENSAGEM_DE_ERRO_NO_PARSE_DO_SALARIO_DO_VENDEDOR = "Não foi possível efetuar o parse do salário do vendedor: {} por alguma inconsistência no layout.  Motivo: {}";

    @Override
    public void processarLinha(String linhaDeVendedor, DadoProcessado dadoProcessado) {

            String[] vendedorSemSeparador = separarLinhaParaMontarObjeto(linhaDeVendedor);

            try {
                if (vendedorSemSeparador.length == 4) {
                    Vendedor vendedor = new Vendedor();
                    vendedor.setCpf(vendedorSemSeparador[1]);
                    vendedor.setNome(vendedorSemSeparador[2]);
                    vendedor.setSalario(Double.parseDouble(removerLetrasDoSalario(vendedorSemSeparador[3])));
                    dadoProcessado.addVendedor(vendedor);
                } else
                    throw new LayoutDeVendedorDiferenteDoEsperadoException();

            } catch (LayoutDeVendedorDiferenteDoEsperadoException e) {
                LOGGER.error(MENSAGEM_DE_ERRO_NO_LAYOUT_DO_VENDEDOR, linhaDeVendedor, e.getCause());
            } catch (NumberFormatException n) {
                LOGGER.error(MENSAGEM_DE_ERRO_NO_PARSE_DO_SALARIO_DO_VENDEDOR, linhaDeVendedor, n.getCause());
            }
        }

    private String removerLetrasDoSalario(String salario) {
        return salario.replaceAll(REGEX_RETIRA_LETRAS_CAMPO_SALARIO, "");
    }
}
