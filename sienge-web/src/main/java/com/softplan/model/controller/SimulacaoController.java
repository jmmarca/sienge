package com.softplan.model.controller;

import com.softplan.model.entidade.Simulacao;
import com.softplan.model.util.JsfUtil;
import com.softplan.model.util.JsfUtil.PersistAction;
import com.softplan.model.bean.SimulacaoFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
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
    private com.softplan.model.bean.SimulacaoFacade ejbFacade;
    private List<Simulacao> items = null;
    private Simulacao selected;

    public SimulacaoController() {
    }

    public Simulacao getSelected() {
        return selected;
    }

    public void setSelected(Simulacao selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private SimulacaoFacade getFacade() {
        return ejbFacade;
    }

    public Simulacao prepareCreate() {
        selected = new Simulacao();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/sienge").getString("SimulacaoCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/sienge").getString("SimulacaoUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/sienge").getString("SimulacaoDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Simulacao> getItems() {
        if (items == null) {
            items = getFacade().listarTodos();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().salvar(selected);
                } else {
                    getFacade().remover(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/sienge").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/sienge").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Simulacao getSimulacao(java.lang.Integer id) {
        return getFacade().encontrar(id);
    }

    public List<Simulacao> getItemsAvailableSelectMany() {
        return getFacade().listarTodos();
    }

    public List<Simulacao> getItemsAvailableSelectOne() {
        return getFacade().listarTodos();
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

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
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
