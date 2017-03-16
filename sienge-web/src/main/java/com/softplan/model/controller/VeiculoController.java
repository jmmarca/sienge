package com.softplan.model.controller;

import com.softplan.model.entidade.Veiculo;
import com.softplan.model.util.JsfUtil;
import com.softplan.model.util.JsfUtil.PersistAction;
import com.softplan.model.bean.VeiculoFacade;

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

@Named("veiculoController")
@SessionScoped
public class VeiculoController implements Serializable {

    @EJB
    private com.softplan.model.bean.VeiculoFacade ejbFacade;
    private List<Veiculo> items = null;
    private Veiculo selected;

    public VeiculoController() {
    }

    public Veiculo getSelected() {
        return selected;
    }

    public void setSelected(Veiculo selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private VeiculoFacade getFacade() {
        return ejbFacade;
    }

    public Veiculo prepareCreate() {
        selected = new Veiculo();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/sienge").getString("VeiculoCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/sienge").getString("VeiculoUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/sienge").getString("VeiculoDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Veiculo> getItems() {
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

    public Veiculo getVeiculo(java.lang.Integer id) {
        return getFacade().encontrar(id);
    }

    public List<Veiculo> getItemsAvailableSelectMany() {
        return getFacade().listarTodos();
    }

    public List<Veiculo> getItemsAvailableSelectOne() {
        return getFacade().listarTodos();
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
