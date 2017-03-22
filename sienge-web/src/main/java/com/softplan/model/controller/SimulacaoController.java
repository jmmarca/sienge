package com.softplan.model.controller;

import com.softplan.model.entidade.Simulacao;
import com.softplan.model.util.WebUtil;
import com.softplan.model.bean.SimulacaoBean;
import com.softplan.model.classe.SimulacaoConfiguracaoPojo;
import com.softplan.model.entidade.SimulacaoItem;
import com.softplan.model.entidade.Veiculo;
import com.softplan.model.generic.AppException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.context.FacesContext;

@Named("simulacaoController")
@SessionScoped
public class SimulacaoController implements Serializable {

    @EJB
    private SimulacaoBean simulacaoBean;

    private List<Simulacao> listaSimulacoes = null;

    private Simulacao simulacaoSelecionada;

    private Veiculo veiculoSelecionado;

    private Integer pesoTransportado;

    private Integer distanciaNaoPavimentada;

    private Integer distanciaPavimentada;

    private SimulacaoConfiguracaoPojo simulacaoConfig;

    public Integer getPesoTransportado() {
        return pesoTransportado;
    }

    public void setPesoTransportado(Integer pesoTransportado) {
        this.pesoTransportado = pesoTransportado;
    }

    public Integer getDistanciaNaoPavimentada() {
        return distanciaNaoPavimentada;
    }

    public void setDistanciaNaoPavimentada(Integer distanciaNaoPavimentada) {
        this.distanciaNaoPavimentada = distanciaNaoPavimentada;
    }

    public Integer getDistanciaPavimentada() {
        return distanciaPavimentada;
    }

    public void setDistanciaPavimentada(Integer distanciaPavimentada) {
        this.distanciaPavimentada = distanciaPavimentada;
    }

    public Simulacao getSimulacao(Integer id) {
        return simulacaoBean.encontrar(id);
    }

    public Veiculo getVeiculoSelecionado() {
        return veiculoSelecionado;
    }

    public void setVeiculoSelecionado(Veiculo veiculoSelecionado) {
        this.veiculoSelecionado = veiculoSelecionado;
    }

    public Simulacao getSimulacaoSelecionada() {
        return simulacaoSelecionada;
    }

    public void setSimulacaoSelecionada(Simulacao simulacaoSelecionada) {
        this.simulacaoSelecionada = simulacaoSelecionada;
    }

    public Simulacao novaSimulacao() {
        simulacaoSelecionada = new Simulacao();
        simulacaoSelecionada.setUsuario(WebUtil.getUsuario());
        simulacaoSelecionada.setItensSimulacao(new ArrayList());
        novoItem();
        return simulacaoSelecionada;
    }

    private void novoItem() {
        veiculoSelecionado = null;
        distanciaNaoPavimentada = 0;
        distanciaPavimentada = 0;
        pesoTransportado = 0;
    }

    public void initView() {
        if (simulacaoSelecionada == null) {
            novaSimulacao();
        }
        if (!simulacaoSelecionada.getItensSimulacao().isEmpty()) {
            recalcular();
        }
    }

    public void salvar() {
        try {
            simulacaoSelecionada.setDhRegistro(new Date());
            simulacaoBean.salvar(simulacaoSelecionada);
            WebUtil.addMsgSucesso("Registro salvo com sucesso!");
            if (!WebUtil.isValido()) {
                listaSimulacoes = null;
            }
            novaSimulacao();
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            WebUtil.addMsgErro("Erro ao salvar!");
        }
    }

    public void remover() {
        try {
            simulacaoBean.remover(simulacaoSelecionada);
            if (!WebUtil.isValido()) {
                simulacaoSelecionada = null;
                listaSimulacoes = null;
            }
            WebUtil.addMsgSucesso("Removido com sucesso!");
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            WebUtil.addMsgErro("Erro ao remover!");
        }
    }

    public List<Simulacao> getListaSimulacoes() {
        if (listaSimulacoes == null) {
            listaSimulacoes = simulacaoBean.listarTodos();
        }
        return listaSimulacoes;
    }

    public void adicionarItem() {
        try {
            if (veiculoSelecionado == null) {
                throw new AppException("Favor Informar o veículo");
            }
            if (pesoTransportado == null || pesoTransportado == 0) {
                throw new AppException("Favor Informar o peso da carga");
            }
            distanciaNaoPavimentada = (distanciaNaoPavimentada == null) ? 0 : distanciaNaoPavimentada;
            distanciaPavimentada = (distanciaPavimentada == null) ? 0 : distanciaPavimentada;

            if (distanciaNaoPavimentada == 0 && distanciaPavimentada == 0) {
                throw new AppException("Favor Informar a distância");
            }
            final SimulacaoItem simulacaoItem = new SimulacaoItem();
            simulacaoItem.setVeiculo(veiculoSelecionado);
            simulacaoItem.setDistanciaSemPavimento(this.distanciaNaoPavimentada);
            simulacaoItem.setDistanciaPavimento(this.distanciaPavimentada);
            simulacaoItem.setSimulacao(simulacaoSelecionada);
            simulacaoItem.setPesoCarga(pesoTransportado);
            simulacaoItem.setValorPavimento(0d);
            simulacaoItem.setValorSemPavimento(0d);
            simulacaoItem.setValorTotal(0d);
            simulacaoSelecionada.getItensSimulacao().add(simulacaoItem);
            recalcular();
            novoItem();
        } catch (AppException ex) {
            WebUtil.addMsgErro(ex.getMessage());
        } catch (Exception ex) {
            WebUtil.addMsgErro("Erro ao adicionar item");
        }
    }

    public void removerItem(SimulacaoItem item) {
        try {
            simulacaoSelecionada.getItensSimulacao().remove(item);
            recalcular();
            WebUtil.addMsgSucesso("Item Removido com sucesso!");
        } catch (Exception ex) {
            WebUtil.addMsgErro("Erro ao remover item");
        }
    }

    public void recalcular() {
        try {
            if (simulacaoConfig == null) {
                simulacaoConfig = simulacaoBean.getNewConfigSimulacao();
            }

            simulacaoSelecionada = simulacaoBean.calcularCustoViagem(simulacaoSelecionada, simulacaoConfig);
        } catch (AppException ex) {
            WebUtil.addMsgErro(ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            WebUtil.addMsgErro("Erro ao efetuar cálculo");
        }
    }

    public void onRowDblSelect() {
        //redireciona para edição
        ConfigurableNavigationHandler handler = (ConfigurableNavigationHandler) FacesContext.getCurrentInstance().getApplication().getNavigationHandler();
        handler.performNavigation("simulador?faces-redirect=true");
    }

}
