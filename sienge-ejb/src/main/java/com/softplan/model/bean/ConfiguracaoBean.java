package com.softplan.model.bean;

import com.softplan.model.entidade.Configuracao;
import com.softplan.model.generic.AppException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Michel
 */
@Stateless
public class ConfiguracaoBean extends AbstractBean<Configuracao> {

    @PersistenceContext(unitName = "sienge-1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ConfiguracaoBean() {
        super(Configuracao.class);
    }

    public Configuracao encontrarPorChave(String chave) throws AppException {
        try {
            javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
            cq.select(cq.from(Configuracao.class));
            javax.persistence.Query q = getEntityManager().createQuery(cq);
            q.setParameter("chave", chave);
            return (Configuracao) q.getSingleResult();
        } catch (NoResultException e) {
            throw new AppException("Não foi encontrada configuração com a chave: " + chave);
        }
    }

}
