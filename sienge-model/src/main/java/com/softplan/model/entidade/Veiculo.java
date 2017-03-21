package com.softplan.model.entidade;

import com.softplan.model.generic.Entidade;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Michel
 */
@Entity
@Table(name = "veiculo", catalog = "postgres")
public class Veiculo extends Entidade {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Integer id;

    @Column(length = 255, name = "descricao")
    private String descricao;

    @Column(name = "fator_multiplicador", precision = 12, scale = 2)
    private Double fatorMultiplicador;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getFatorMultiplicador() {
        return fatorMultiplicador;
    }

    public void setFatorMultiplicador(Double fatorMultiplicador) {
        this.fatorMultiplicador = fatorMultiplicador;
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
        if (!(object instanceof Veiculo)) {
            return false;
        }
        Veiculo other = (Veiculo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

}
