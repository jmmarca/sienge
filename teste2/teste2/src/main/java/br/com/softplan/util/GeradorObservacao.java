package br.com.softplan.util;

import java.util.List;

public class GeradorObservacao {

    public String geraObservacao(List<Integer> lista) {
        if (lista != null && !lista.isEmpty()) {
            return retornaMensagem(lista) + ".";
        }
        return "";
    }

    //Cria observação
    private String retornaMensagem(List<Integer> lista) {
        String texto;
        if (lista.size() > 1) {
            texto = "Fatura das notas fiscais de simples remessa: ";
        } else {
            texto = "Fatura da nota fiscal de simples remessa: ";
        }

        //Acha separador
        StringBuilder codigos = new StringBuilder();
        String separador;
        int count = 0;
        int ultimo = lista.size() - 1;
        for (Integer nfNumero : lista) {
            if (count == 0) {
                separador = "";
            } else if (count < ultimo) {
                separador = ", ";
            } else {
                separador = " e ";
            }
            codigos.append(separador).append(nfNumero);
            count++;
        }

        return texto + codigos.toString();
    }
}
