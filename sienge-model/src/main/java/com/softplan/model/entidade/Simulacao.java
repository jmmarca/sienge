package com.softplan.model.entidade;

import com.softplan.model.generic.Entidade;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Michel
 */
@Entity(name = "simulacao")
@Table(catalog = "postgres", schema = "public")
public class Simulacao extends Entidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "dh_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dhRegistro;

    @JoinColumn(name = "id_veiculo", referencedColumnName = "id")
    @ManyToOne
    private Veiculo veiculo;

    @OneToMany(mappedBy = "simulacao")
    private List<SimulacaoItem> itensSimulacao;

    public Simulacao() {
    }

    public Simulacao(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDhRegistro() {
        return dhRegistro;
    }

    public void setDhRegistro(Date dhRegistro) {
        this.dhRegistro = dhRegistro;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public List<SimulacaoItem> getItensSimulacao() {
        return itensSimulacao;
    }

    public void setItensSimulacao(List<SimulacaoItem> itensSimulacao) {
        this.itensSimulacao = itensSimulacao;
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
        if (!(object instanceof Simulacao)) {
            return false;
        }
        Simulacao other = (Simulacao) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

}
