/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softplan.model.bean;

import com.softplan.model.entidade.Veiculo;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Michel
 */
@Stateless
public class VeiculoBean extends AbstractBean<Veiculo> {

    @PersistenceContext(unitName = "sienge-1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VeiculoBean() {
        super(Veiculo.class);
    }
    
}
