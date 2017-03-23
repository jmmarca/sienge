package com.softplan.model.bean;

import com.softplan.model.entidade.Configuracao;
import com.softplan.model.generic.AppException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
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
            return (Configuracao) em.createQuery(
                    "SELECT c FROM Configuracao c WHERE c.chave=:chave")
                    .setParameter("chave", chave)
                    .setFirstResult(0).getSingleResult();
        } catch (NonUniqueResultException e) {
            throw new AppException("Mais de uma chave para mesma configuração com a chave: " + chave);
        } catch (NoResultException e) {
            throw new AppException("Não foi encontrada configuração com a chave: " + chave);
        }
    }

}
