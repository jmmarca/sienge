package com.softplan.model.bean;

import com.softplan.model.entidade.Simulacao;
import com.softplan.model.entidade.SimulacaoItem;
import com.softplan.model.entidade.Veiculo;
import com.softplan.model.generic.AppException;
import com.softplan.model.util.Functions;

import java.util.List;
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

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SimulacaoBean() {
        super(Simulacao.class);
    }

    /**
     *
     * @param simulacao
     * @return
     */
    public Simulacao calcularCustoViagem(Simulacao simulacao) throws AppException {
        
        final List<Simulacao> listarTodos = listarTodos();
        
        //itens por tipo de via
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
                Double custoRodoviaNaoPavimentada = 0.62;
                Double custoRodoviaPavimentada = 0.54;

                Double valorRodoviaPavimentada = calcularPorTipoRodovia(custoRodoviaPavimentada, distanciaPavimentada, fatorMultiplicador, peso);
                Double valorRodoviaNaoPavimentada = calcularPorTipoRodovia(custoRodoviaNaoPavimentada, distanciaNaoPavimentada, fatorMultiplicador, peso);
                simulacaoItem.setValorPavimento(valorRodoviaPavimentada);
                simulacaoItem.setValorSemPavimento(valorRodoviaNaoPavimentada);
                simulacaoItem.setValorTotal(valorRodoviaPavimentada + valorRodoviaNaoPavimentada);
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
    private Double calcularPorTipoRodovia(Double custoKmRodado, Integer distanciaPercorrida, Double fatorMultiplicador, Integer peso) {

        Double valorCalculado = distanciaPercorrida * (custoKmRodado);
        valorCalculado *= fatorMultiplicador;
        valorCalculado = Functions.round(valorCalculado, 2);
        //Adiciona a diferença por excesso de peso por km rodado
        if (peso > 5) {
            Integer diff = peso - 5;
            Double excedente = diff * 0.02;
            valorCalculado += (distanciaPercorrida * excedente);
        }
        return valorCalculado;
    }

}
