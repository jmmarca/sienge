package com.softplan.model.bean;

import com.softplan.model.classe.SimulacaoConfiguracaoPojo;
import com.softplan.model.entidade.Configuracao;
import com.softplan.model.entidade.Simulacao;
import com.softplan.model.entidade.SimulacaoItem;
import com.softplan.model.entidade.Veiculo;
import com.softplan.model.generic.AppException;
import com.softplan.model.util.Functions;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Michel
 */
@Stateless
public class SimulacaoBean extends AbstractBean<Simulacao> {

    @PersistenceContext(unitName = "sienge-1.0-SNAPSHOTPU")
    private EntityManager em;

    @EJB
    private ConfiguracaoBean configuracaoBean;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public SimulacaoBean() {
        super(Simulacao.class);
    }

    /**
     *
     * @param simulacao
     * @param config
     * @return
     */
    public Simulacao calcularCustoViagem(Simulacao simulacao, SimulacaoConfiguracaoPojo config) throws AppException {

        //itens por tipo de rodovia
        final List<SimulacaoItem> itensSimulacao = simulacao.getItensSimulacao();
        Double total = 0d;
        for (SimulacaoItem simulacaoItem : itensSimulacao) {
            Veiculo veiculo = simulacaoItem.getVeiculo();
            if (veiculo == null) {
                throw new AppException("Favor Informar o veículo");
            }
            Integer peso = simulacaoItem.getPesoCarga();

            if (peso == null || peso == 0) {
                throw new AppException("Favor Informar o peso da carga");
            }
            Integer distanciaNaoPavimentada = simulacaoItem.getDistanciaSemPavimento();
            Integer distanciaPavimentada = simulacaoItem.getDistanciaPavimento();
            final Double fatorMultiplicador = veiculo.getFatorMultiplicador();
            distanciaNaoPavimentada = (distanciaNaoPavimentada == null) ? 0 : distanciaNaoPavimentada;
            distanciaPavimentada = (distanciaPavimentada == null) ? 0 : distanciaPavimentada;

            if (distanciaNaoPavimentada > 0 || distanciaPavimentada > 0) {
                //Custo do Km rodado
                Double custoRodoviaNaoPavimentada = config.getValorRodoviaNaoPavimentada();
                Double custoRodoviaPavimentada = config.getValorRodoviaPavimentada();

                Double valorRodoviaPavimentada = calcularPorTipoRodovia(config, custoRodoviaPavimentada, distanciaPavimentada, fatorMultiplicador, peso);
                Double valorRodoviaNaoPavimentada = calcularPorTipoRodovia(config, custoRodoviaNaoPavimentada, distanciaNaoPavimentada, fatorMultiplicador, peso);
                simulacaoItem.setValorPavimento(valorRodoviaPavimentada);
                simulacaoItem.setValorSemPavimento(valorRodoviaNaoPavimentada);
                simulacaoItem.setValorTotal(Functions.round(valorRodoviaPavimentada + valorRodoviaNaoPavimentada, 2));
                total += (simulacaoItem.getValorTotal());
            } else {
                simulacaoItem.setDistanciaPavimento(0);
                simulacaoItem.setDistanciaSemPavimento(0);
                simulacaoItem.setValorTotal(0d);
            }
        }
        simulacao.setValorTotal(total);

        return simulacao;
    }

    /**
     *
     * Retorna o Valor de acordo com o tipo de rodovia
     *
     * @param simulacaoItem
     * @param custoKmRodado
     * @param distanciaPercorrida
     * @param fatorMultiplicador
     * @param peso
     * @return
     */
    private Double calcularPorTipoRodovia(SimulacaoConfiguracaoPojo config, Double custoKmRodado, Integer distanciaPercorrida, Double fatorMultiplicador, Integer peso) {
        Double valorCalculado = distanciaPercorrida * (custoKmRodado);
        valorCalculado *= fatorMultiplicador;
        //Adiciona a diferença por excesso de peso por km rodado
        if (peso > config.getLimitePeso()) {
            //Diferença de excesso de peso
            Integer diff = peso - config.getLimitePeso();
            //Valor de acordo com o excesso de peso da carga
            Double excedente = diff * config.getValorExcessoCarga();
            //Adicionar o excesso ao valor de acordo com a distância percorrida
            valorCalculado += (distanciaPercorrida * excedente);
        }
        valorCalculado = Functions.round(valorCalculado, 2);
        return valorCalculado;
    }

    public SimulacaoConfiguracaoPojo getNewConfigSimulacao() throws AppException {
        Configuracao valorRodoviaPavimentada = configuracaoBean.encontrarPorChave("valor_pavimentada");
        Configuracao valorRodoviaNaoPavimentada = configuracaoBean.encontrarPorChave("valor_nao_pavimentada");
        Configuracao limitePeso = configuracaoBean.encontrarPorChave("limite_peso");
        Configuracao valorKmExcessoPeso = configuracaoBean.encontrarPorChave("valor_km_excesso_peso");

        SimulacaoConfiguracaoPojo config = new SimulacaoConfiguracaoPojo();
        config.setLimitePeso(Integer.valueOf(limitePeso.getValor()));
        config.setValorExcessoCarga(Double.valueOf(valorKmExcessoPeso.getValor()));
        config.setValorRodoviaNaoPavimentada(Double.valueOf(valorRodoviaNaoPavimentada.getValor()));
        config.setValorRodoviaPavimentada(Double.valueOf(valorRodoviaPavimentada.getValor()));

        return config;
    }

}
