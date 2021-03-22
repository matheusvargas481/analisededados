package com.matheusvargas481.analisededados.util;

public abstract class Separador {
    private static final String CEDILHA_CARACTER_ESPECIAL = "ç(?![a-záéíóúàèìòùâêîôûãõç])";
    protected String[] separarLinhaParaMontarObjeto(String linhaDoArquivo) {
        String separador = linhaDoArquivo.substring(3, 4);

        if (separador.equalsIgnoreCase("ç"))
            separador = CEDILHA_CARACTER_ESPECIAL;

        return linhaDoArquivo.split(separador);
    }


}
