package com.softplan.model.controller;

import com.softplan.model.entidade.Veiculo;
import com.softplan.model.util.WebUtil;
import com.softplan.model.bean.VeiculoBean;

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

@Named("veiculoController")
@SessionScoped
public class VeiculoController implements Serializable {

    @EJB
    private VeiculoBean veiculoBean;
    private List<Veiculo> listaVeiculos = null;
    private Veiculo selecionado;

    public VeiculoController() {
    }

    public Veiculo getSelecionado() {
        return selecionado;
    }

    public void setSelecionado(Veiculo selecionado) {
        this.selecionado = selecionado;
    }

    public Veiculo novoVeiculo() {
        selecionado = new Veiculo();
        return selecionado;
    }

    public void salvar() {
        try {
            veiculoBean.salvar(selecionado);
            WebUtil.addMsgSucesso("Registro salvo com sucesso!");
            if (!WebUtil.isValido()) {
                listaVeiculos = null;
            }
            selecionado = null;

        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            WebUtil.addMsgErro("Erro ao salvar!");
        }
    }

    public void remover() {
        try {
            veiculoBean.remover(selecionado);
            if (!WebUtil.isValido()) {
                selecionado = null;
                listaVeiculos = null;
            }
            WebUtil.addMsgSucesso("Removido com sucesso!");
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            WebUtil.addMsgErro("Erro ao remover!");
        }
    }

    public List<Veiculo> getListaVeiculos() {
        if (listaVeiculos == null) {
            listaVeiculos = veiculoBean.listarTodos();
        }
        return listaVeiculos;
    }

    public Veiculo getVeiculo(Integer id) {
        return veiculoBean.encontrar(id);
    }

    public List<Veiculo> getItemsAvailableSelectMany() {
        return veiculoBean.listarTodos();
    }

    public List<Veiculo> getItemsAvailableSelectOne() {
        return veiculoBean.listarTodos();
    }

    @FacesConverter(forClass = Veiculo.class)
    public static class VeiculoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            VeiculoController controller = (VeiculoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "veiculoController");
            return controller.getVeiculo(getKey(value));
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
            if (object instanceof Veiculo) {
                Veiculo o = (Veiculo) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Veiculo.class.getName()});
                return null;
            }
        }

    }

}
