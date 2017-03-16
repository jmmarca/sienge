package com.softplan.model.entidade;

import com.softplan.model.generic.Entidade;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Michel
 */
@Entity
@Table(name = "simulacao_item", catalog = "postgres", schema = "public")

public class SimulacaoItem extends Entidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(precision = 12, scale = 2)
    private BigDecimal distancia;

    @Column(name = "peso_carga", precision = 12, scale = 2)
    private BigDecimal pesoCarga;

    @Column(name = "valor_total", precision = 12, scale = 2)
    private BigDecimal valorTotal;

    @JoinColumn(name = "id_rodovia", referencedColumnName = "id")
    @ManyToOne
    private RodoviaTabelaPreco rodovia;

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

    public BigDecimal getDistancia() {
        return distancia;
    }

    public void setDistancia(BigDecimal distancia) {
        this.distancia = distancia;
    }

    public BigDecimal getPesoCarga() {
        return pesoCarga;
    }

    public void setPesoCarga(BigDecimal pesoCarga) {
        this.pesoCarga = pesoCarga;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public RodoviaTabelaPreco getIdRodovia() {
        return rodovia;
    }

    public void setIdRodovia(RodoviaTabelaPreco idRodovia) {
        this.rodovia = idRodovia;
    }

    public Simulacao getIdSimulacao() {
        return simulacao;
    }

    public void setIdSimulacao(Simulacao idSimulacao) {
        this.simulacao = idSimulacao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SimulacaoItem)) {
            return false;
        }
        SimulacaoItem other = (SimulacaoItem) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

}
