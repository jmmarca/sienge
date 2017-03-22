package com.softplan.model.classe;

/**
 * Pojo para abstrair as variáveis de configuração do teste que deve ser
 * efetuado.
 *
 * @author Michel
 */
public class SimulacaoConfiguracaoPojo {

    /**
     * Limite de peso
     */
    private Integer limitePeso;

    private Double valorExcessoCarga;

    private Double valorRodoviaPavimentada;

    private Double valorRodoviaNaoPavimentada;

    public Integer getLimitePeso() {
        return limitePeso;
    }

    public void setLimitePeso(Integer limitePeso) {
        this.limitePeso = limitePeso;
    }

    public Double getValorExcessoCarga() {
        return valorExcessoCarga;
    }

    public void setValorExcessoCarga(Double valorExcessoCarga) {
        this.valorExcessoCarga = valorExcessoCarga;
    }

    public Double getValorRodoviaPavimentada() {
        return valorRodoviaPavimentada;
    }

    public void setValorRodoviaPavimentada(Double valorRodoviaPavimentada) {
        this.valorRodoviaPavimentada = valorRodoviaPavimentada;
    }

    public Double getValorRodoviaNaoPavimentada() {
        return valorRodoviaNaoPavimentada;
    }

    public void setValorRodoviaNaoPavimentada(Double valorRodoviaNaoPavimentada) {
        this.valorRodoviaNaoPavimentada = valorRodoviaNaoPavimentada;
    }

}
