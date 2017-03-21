
import com.softplan.model.bean.SimulacaoBean;
import com.softplan.model.entidade.Simulacao;
import com.softplan.model.entidade.SimulacaoItem;
import com.softplan.model.entidade.Usuario;
import com.softplan.model.entidade.Veiculo;
import com.softplan.model.generic.AppException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import junit.framework.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class SimulacaoTest {

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class, "test.jar")
                .addAsResource("META-INF/persistence.xml","META-INF/persistence.xml")
                .addPackage(SimulacaoBean.class.getPackage())
                .addPackage(Simulacao.class.getPackage())
                
                ;
    }

    @EJB
    SimulacaoBean repo;

    @Test
    @InSequence(1)
    public void place_order_should_add_order() throws AppException {

//         //script de importação
//        Usuario user = new Usuario();
//        user.setNome("Administrador");
//        user.setLogin("admin");
//        user.setSenha("admin");
//        user.setEmail("teste@sienge.com.br");
//        
//
//        Configuracao config = new Configuracao();
//        config.setChave("valor_pavimentada");
//        config.setValor("0.54");
//        
//
//        config = new Configuracao();
//        config.setChave("valor_nao_pavimentada");
//        config.setValor("0.62");
//        
//        
//        config = new Configuracao();
//        config.setChave("limite_peso");
//        config.setValor("5");
//       
//        
//        config = new Configuracao();
//        config.setChave("valor_km_excesso_peso");
//        config.setValor("0.02");
//       
        Veiculo veiculo = new Veiculo();
        veiculo.setDescricao("Caminhão baú");
        veiculo.setFatorMultiplicador(new Double("1.00"));

        veiculo = new Veiculo();
        veiculo.setDescricao("Caminhão caçamba");
        veiculo.setFatorMultiplicador(new Double("1.05"));

        veiculo = new Veiculo();
        veiculo.setDescricao("Carreta");
        veiculo.setFatorMultiplicador(new Double("1.12"));

        Simulacao simulacao = new Simulacao();
        List<SimulacaoItem> itens = new ArrayList<SimulacaoItem>();

        SimulacaoItem item = new SimulacaoItem();
        item.setVeiculo(veiculo);
        item.setDistanciaSemPavimento(180);
        item.setPesoCarga(12);
        itens.add(item);
        simulacao.setItensSimulacao(itens);

        final Simulacao calcularCustoViagem = repo.calcularCustoViagem(simulacao);

        Assert.assertEquals(150.19, (double) calcularCustoViagem.getValorTotal());

    }
}
