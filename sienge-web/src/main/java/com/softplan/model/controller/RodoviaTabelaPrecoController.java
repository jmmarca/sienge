package com.softplan.model.controller;

import com.softplan.model.entidade.RodoviaTabelaPreco;
import com.softplan.model.util.WebUtil;
import com.softplan.model.bean.RodoviaTabelaPrecoBean;

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

@Named("rodoviaTabelaPrecoController")
@SessionScoped
public class RodoviaTabelaPrecoController implements Serializable {

    @EJB
    private RodoviaTabelaPrecoBean rodoviaTabelaPrecoBean;
    private List<RodoviaTabelaPreco> listaRodoviaTabelaPreco = null;
    private RodoviaTabelaPreco selecionada;

    public RodoviaTabelaPrecoController() {
    }

    public RodoviaTabelaPreco getSelecionada() {
        return selecionada;
    }

    public void setSelecionada(RodoviaTabelaPreco selecionada) {
        this.selecionada = selecionada;
    }

    public RodoviaTabelaPreco novaRodoviaTabelaPreco() {
        selecionada = new RodoviaTabelaPreco();
        return selecionada;
    }

    public void salvar() {
        try {
            rodoviaTabelaPrecoBean.salvar(selecionada);
            WebUtil.addMsgSucesso("Registro salvo com sucesso!");
            if (!WebUtil.isValido()) {
                listaRodoviaTabelaPreco = null;
            }

        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            WebUtil.addMsgErro("Erro ao salvar!");
        }
    }

    public void remover() {
        try {
            rodoviaTabelaPrecoBean.remover(selecionada);
            if (!WebUtil.isValido()) {
                selecionada = null;
                listaRodoviaTabelaPreco = null;
            }
            WebUtil.addMsgSucesso("Removido com sucesso!");
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            WebUtil.addMsgErro("Erro ao remover!");
        }
    }

    public List<RodoviaTabelaPreco> getListaRodoviaTabelaPreco() {
        if (listaRodoviaTabelaPreco == null) {
            listaRodoviaTabelaPreco = rodoviaTabelaPrecoBean.listarTodos();
        }
        return listaRodoviaTabelaPreco;
    }

    public RodoviaTabelaPreco getRodoviaTabelaPreco(Integer id) {
        return rodoviaTabelaPrecoBean.encontrar(id);
    }

    public List<RodoviaTabelaPreco> getItemsAvailableSelectMany() {
        return rodoviaTabelaPrecoBean.listarTodos();
    }

    public List<RodoviaTabelaPreco> getItemsAvailableSelectOne() {
        return rodoviaTabelaPrecoBean.listarTodos();
    }

    @FacesConverter(forClass = RodoviaTabelaPreco.class)
    public static class RodoviaTabelaPrecoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RodoviaTabelaPrecoController controller = (RodoviaTabelaPrecoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "rodoviaTabelaPrecoController");
            return controller.getRodoviaTabelaPreco(getKey(value));
        }

        public Integer getKey(String value) {
            Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        public String getStringKey(Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof RodoviaTabelaPreco) {
                RodoviaTabelaPreco o = (RodoviaTabelaPreco) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), RodoviaTabelaPreco.class.getName()});
                return null;
            }
        }

    }

}
