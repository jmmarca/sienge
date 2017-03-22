package com.softplan.model.bean;

import com.softplan.model.entidade.Configuracao;
import com.softplan.model.entidade.Usuario;
import com.softplan.model.entidade.Veiculo;
import com.softplan.model.generic.Entidade;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Michel
 */
@Stateless
public class EntidadeBean extends AbstractBean<Entidade> {

    @PersistenceContext(unitName = "sienge-1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EntidadeBean() {
        super(Entidade.class);
    }

    /**
     * Método que gera o DB e inicializa as tabelas
     */
    public void inicializarDB() {

        //script de importação
        Usuario user = new Usuario();
        user.setNome("Administrador");
        user.setLogin("admin");
        user.setSenha("admin");
        user.setEmail("teste@sienge.com.br");
        salvar(user);

        Configuracao config = new Configuracao();
        config.setChave("valor_pavimentada");
        config.setValor("0.54");
        salvar(config);

        config = new Configuracao();
        config.setChave("valor_nao_pavimentada");
        config.setValor("0.62");
        salvar(config);
        
        config = new Configuracao();
        config.setChave("limite_peso");
        config.setValor("5");
        salvar(config);
        
        config = new Configuracao();
        config.setChave("valor_km_excesso_peso");
        config.setValor("0.02");
        salvar(config);

        Veiculo veiculo = new Veiculo();
        veiculo.setDescricao("Caminhão baú");
        veiculo.setFatorMultiplicador(1.00);
        salvar(veiculo);

        veiculo = new Veiculo();
        veiculo.setDescricao("Caminhão caçamba");
        veiculo.setFatorMultiplicador(1.05);
        salvar(veiculo);
        
        veiculo = new Veiculo();
        veiculo.setDescricao("Carreta");
        veiculo.setFatorMultiplicador(1.12);
        salvar(veiculo);
    }

}
