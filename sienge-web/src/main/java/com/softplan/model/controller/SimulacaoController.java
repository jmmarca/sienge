package com.softplan.model.controller;

import com.softplan.model.entidade.Simulacao;
import com.softplan.model.util.WebUtil;
import com.softplan.model.bean.SimulacaoBean;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("simulacaoController")
@SessionScoped
public class SimulacaoController implements Serializable {

    @EJB
    private SimulacaoBean simulacaoBean;
    private List<Simulacao> listaSimulacoes = null;
    private Simulacao selecionada;

    public SimulacaoController() {
    }

    public Simulacao getSelecionada() {
        return selecionada;
    }

    public void setSelecionada(Simulacao selecionada) {
        this.selecionada = selecionada;
    }

    public Simulacao novaSimulacao() {
        selecionada = new Simulacao();
        return selecionada;
    }

    public void salvar() {
        try {
            simulacaoBean.salvar(selecionada);
            WebUtil.addMsgSucesso("Registro salvo com sucesso!");
            if (!WebUtil.isValido()) {
                listaSimulacoes = null;
            }
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            WebUtil.addMsgErro("Erro ao salvar!");
        }
    }

    public void remover() {
        try {
            simulacaoBean.remover(selecionada);
            if (!WebUtil.isValido()) {
                selecionada = null;
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

    public Simulacao getSimulacao(Integer id) {
        return simulacaoBean.encontrar(id);
    }

    public List<Simulacao> getItemsAvailableSelectMany() {
        return simulacaoBean.listarTodos();
    }

    public List<Simulacao> getItemsAvailableSelectOne() {
        return simulacaoBean.listarTodos();
    }

    @FacesConverter(forClass = Simulacao.class)
    public static class SimulacaoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
}
            SimulacaoController controller = (SimulacaoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "simulacaoController");
            return controller.getSimulacao(getKey(value));
        }

        Integer getKey(String value) {
            Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Simulacao) {
                Simulacao o = (Simulacao) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Simulacao.class.getName()});
                return null;
            }
        }

    }

}
