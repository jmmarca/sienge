package com.softplan.model.controller;

import com.softplan.model.entidade.RodoviaTabelaPreco;
import com.softplan.model.util.JsfUtil;
import com.softplan.model.util.JsfUtil.PersistAction;
import com.softplan.model.bean.RodoviaTabelaPrecoFacade;

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

@Named("rodoviaTabelaPrecoController")
@SessionScoped
public class RodoviaTabelaPrecoController implements Serializable {

    @EJB
    private com.softplan.model.bean.RodoviaTabelaPrecoFacade ejbFacade;
    private List<RodoviaTabelaPreco> items = null;
    private RodoviaTabelaPreco selected;

    public RodoviaTabelaPrecoController() {
    }

    public RodoviaTabelaPreco getSelected() {
        return selected;
    }

    public void setSelected(RodoviaTabelaPreco selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private RodoviaTabelaPrecoFacade getFacade() {
        return ejbFacade;
    }

    public RodoviaTabelaPreco prepareCreate() {
        selected = new RodoviaTabelaPreco();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/sienge").getString("RodoviaTabelaPrecoCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/sienge").getString("RodoviaTabelaPrecoUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/sienge").getString("RodoviaTabelaPrecoDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<RodoviaTabelaPreco> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
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

    public RodoviaTabelaPreco getRodoviaTabelaPreco(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<RodoviaTabelaPreco> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<RodoviaTabelaPreco> getItemsAvailableSelectOne() {
        return getFacade().findAll();
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
