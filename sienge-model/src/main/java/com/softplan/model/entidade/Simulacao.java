package com.softplan.model.entidade;

import com.softplan.model.generic.Entidade;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
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
import javax.persistence.Transient;

/**
 *
 * @author Michel
 */
@Entity(name = "simulacao")
@Table(catalog = "postgres", schema = "public")
public class Simulacao extends Entidade {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Integer id;

    @Column(name = "dh_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dhRegistro;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO")
    private Usuario usuario;

    @OneToMany(mappedBy = "simulacao", orphanRemoval = true, cascade = {CascadeType.ALL})
    private List<SimulacaoItem> itensSimulacao;

    @Transient
    private Double valorTotal;

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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getDhRegistro() {
        return dhRegistro;
    }

    public void setDhRegistro(Date dhRegistro) {
        this.dhRegistro = dhRegistro;
    }

    public List<SimulacaoItem> getItensSimulacao() {
        return itensSimulacao;
    }

    public void setItensSimulacao(List<SimulacaoItem> itensSimulacao) {
        this.itensSimulacao = itensSimulacao;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
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
