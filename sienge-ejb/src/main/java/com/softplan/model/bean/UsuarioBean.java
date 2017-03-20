/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softplan.model.bean;

import com.softplan.model.entidade.Usuario;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

/**
 *
 * @author Michel
 */
@Stateless
public class UsuarioBean extends AbstractBean<Usuario> {

    @PersistenceContext(unitName = "sienge-1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioBean() {
        super(Usuario.class);
    }

    /**
     *
     * @param usuario
     * @param senha
     * @return
     */
    public Usuario logar(Usuario usuario, String senha) {
        try {
            CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
            Query q = getEntityManager().createQuery(cq);
            q.setParameter("usuario", usuario);
            q.setParameter("senha", senha);
            return (Usuario) q.getSingleResult();
        } catch (NoResultException e) {
        }
        return null;
    }

}
