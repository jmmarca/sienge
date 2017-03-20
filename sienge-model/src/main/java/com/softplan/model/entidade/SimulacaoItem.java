package com.softplan.model.entidade;

import com.softplan.model.generic.Entidade;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Michel
 */
@Entity
@Table(name = "simulacao_item", catalog = "postgres", schema = "public")

public class SimulacaoItem extends Entidade {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Integer id;

    @JoinColumn(name = "id_veiculo", referencedColumnName = "id")
    @ManyToOne
    private Veiculo veiculo;

    @Column(name = "peso_carga")
    private Integer pesoCarga;

    @Column(name = "distancia_pavimento", precision = 12, scale = 2)
    private Integer distanciaPavimento;

    @Column(name = "distancia_nao_pavimento", precision = 12, scale = 2)
    private Integer distanciaSemPavimento;

    @Column(name = "valor_pavimento", precision = 12, scale = 2)
    private Double valorPavimento;

    @Column(name = "valor_sem_pavimento", precision = 12, scale = 2)
    private Double valorSemPavimento;

    @Transient
    private Double valorTotal;

    @JoinColumn(name = "id_simulacao", referencedColumnName = "id")
    @ManyToOne
    private Simulacao simulacao;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Double getValorPavimento() {
        return valorPavimento;
    }

    public void setValorPavimento(Double valorPavimento) {
        this.valorPavimento = valorPavimento;
    }

    public Double getValorSemPavimento() {
        return valorSemPavimento;
    }

    public void setValorSemPavimento(Double valorSemPavimento) {
        this.valorSemPavimento = valorSemPavimento;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public Integer getPesoCarga() {
        return pesoCarga;
    }

    public void setPesoCarga(Integer pesoCarga) {
        this.pesoCarga = pesoCarga;
    }

    public Integer getDistanciaPavimento() {
        return distanciaPavimento;
    }

    public void setDistanciaPavimento(Integer distanciaPavimento) {
        this.distanciaPavimento = distanciaPavimento;
    }

    public Integer getDistanciaSemPavimento() {
        return distanciaSemPavimento;
    }

    public void setDistanciaSemPavimento(Integer distanciaSemPavimento) {
        this.distanciaSemPavimento = distanciaSemPavimento;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Simulacao getSimulacao() {
        return simulacao;
    }

    public void setSimulacao(Simulacao simulacao) {
        this.simulacao = simulacao;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

}
