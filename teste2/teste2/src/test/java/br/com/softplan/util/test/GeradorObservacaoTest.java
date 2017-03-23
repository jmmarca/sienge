package br.com.softplan.util.test;

import br.com.softplan.util.GeradorObservacao;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

public class GeradorObservacaoTest {

    private GeradorObservacao geradorObservacao;

    @Before
    public void setUp() {
        geradorObservacao = new GeradorObservacao();
    }

    @Test
    public void geraObservacaoVaziaQuandoNaoExistemNotas() {
        String observacaoGerada = this.geradorObservacao.geraObservacao(Collections.EMPTY_LIST);
        assertTrue(observacaoGerada.isEmpty());
    }

    @Test
    public void geraObservacaoParaUmaNota() {
        String observacaoGerada = this.geradorObservacao.geraObservacao(Arrays.asList(1));

        String observacaoEsperada = "Fatura da nota fiscal de simples remessa: 1.";
        assertEquals(observacaoEsperada, observacaoGerada);
    }

    @Test
    public void geraObservacaoParaDuasNotas() {
        String observacaoGerada = this.geradorObservacao.geraObservacao(Arrays.asList(1, 2));

        String observacaoEsperada = "Fatura das notas fiscais de simples remessa: 1 e 2.";
        assertEquals(observacaoEsperada, observacaoGerada);
    }

    @Test
    public void geraObservacaoParaMultiplasNotas() {
        String observacaoGerada = this.geradorObservacao.geraObservacao(Arrays.asList(1, 2, 3, 4, 5));

        String observacaoEsperada = "Fatura das notas fiscais de simples remessa: 1, 2, 3, 4 e 5.";
        assertEquals(observacaoEsperada, observacaoGerada);
    }
}
