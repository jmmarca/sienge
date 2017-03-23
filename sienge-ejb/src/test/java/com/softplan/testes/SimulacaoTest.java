package com.softplan.testes;
        
import com.softplan.model.bean.ConfiguracaoBean;
import com.softplan.model.bean.SimulacaoBean;
import com.softplan.model.classe.SimulacaoConfiguracaoPojo;
import com.softplan.model.entidade.Simulacao;
import com.softplan.model.entidade.SimulacaoItem;
import com.softplan.model.entidade.Veiculo;
import com.softplan.model.generic.AppException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.EJB;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Essa classe de teste utiliza Arquillian para prover teste com conteiner real
 * utilizando EJB, assim é possível fazer testes de regra de negócio e até mesmo
 * fazer testes funcionais, utilizando selenium
 *
 * @author Michel
 */
@RunWith(Arquillian.class)
@Transactional
public class SimulacaoTest {

    @EJB
    private SimulacaoBean simulacaoBean;
    
    @EJB
    private ConfiguracaoBean configuracaoBean;

    private SimulacaoConfiguracaoPojo config;

    /**
     * Mapa que testa o resultado esperado de acordo com a tabela apresentada
     *
     */
    private Map<SimulacaoItem, Double> mapaSimulacaoItemResultado;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class, "test.jar")
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                .addPackage(SimulacaoBean.class.getPackage())
                .addPackage(Simulacao.class.getPackage());
    }

    @Before
    public void setUp() {
        mapaSimulacaoItemResultado = new HashMap();
        /**
         * poderia fazer testes utilizando banco de dados, mas utilizei uma
         * abordagem de deixar os dados fixos e assim garantir que o retorno
         * seja igual ao proposto
         */
        config = new SimulacaoConfiguracaoPojo();
        config.setLimitePeso(5);
        config.setValorExcessoCarga(0.02);
        config.setValorRodoviaPavimentada(0.54);
        config.setValorRodoviaNaoPavimentada(0.62);

        Veiculo caminhaoBau = new Veiculo();
        caminhaoBau.setDescricao("Caminhão baú");
        caminhaoBau.setFatorMultiplicador(1d);

        Veiculo caminhaoCacamba = new Veiculo();
        caminhaoCacamba.setDescricao("Caminhão caçamba");
        caminhaoCacamba.setFatorMultiplicador(1.05);

        Veiculo carreta = new Veiculo();
        carreta.setDescricao("Carreta");
        carreta.setFatorMultiplicador(1.12);

        //Aqui monto a tabela de testes esperados 
        SimulacaoItem item = new SimulacaoItem();
        item.setVeiculo(caminhaoCacamba);
        item.setDistanciaPavimento(100);
        item.setPesoCarga(8);//em toneladas 
        mapaSimulacaoItemResultado.put(item, 62.70d);

        //neste caso não passou do limite
        item = new SimulacaoItem();
        item.setVeiculo(caminhaoBau);
        item.setDistanciaSemPavimento(60);
        item.setPesoCarga(4);
        mapaSimulacaoItemResultado.put(item, 37.20d);

        item = new SimulacaoItem();
        item.setVeiculo(carreta);
        item.setDistanciaSemPavimento(180);
        item.setPesoCarga(12);
        mapaSimulacaoItemResultado.put(item, 150.19d);

        item = new SimulacaoItem();
        item.setVeiculo(caminhaoBau);
        item.setDistanciaPavimento(80);
        item.setDistanciaSemPavimento(20);
        item.setPesoCarga(6);
        mapaSimulacaoItemResultado.put(item, 57.60d);

        item = new SimulacaoItem();
        item.setVeiculo(caminhaoCacamba);
        item.setDistanciaPavimento(50);
        item.setDistanciaSemPavimento(30);
        item.setPesoCarga(5);
        mapaSimulacaoItemResultado.put(item, 47.88d);

    }

    @Test
    @InSequence(1)
    @Transactional(TransactionMode.ROLLBACK)
    public void testeSimulacao() throws AppException {
        //Teste Unitário, acessando EJB e validando item a item
        for (Map.Entry<SimulacaoItem, Double> entry : mapaSimulacaoItemResultado.entrySet()) {
            Simulacao simulacao = new Simulacao();
            simulacao.setItensSimulacao(new ArrayList());
            SimulacaoItem item = entry.getKey();
            Double valorEsperado = entry.getValue();
            simulacao.getItensSimulacao().add(item);
            final Simulacao calcularCustoViagem = simulacaoBean.calcularCustoViagem(simulacao, config);
            Assert.assertEquals("Problema no Cálculo, valores divergentes", valorEsperado, calcularCustoViagem.getValorTotal());
        }
    }
}
