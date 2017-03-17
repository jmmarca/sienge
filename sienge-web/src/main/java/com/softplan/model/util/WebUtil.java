package com.softplan.model.util;

import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;

public class WebUtil {

    public static SelectItem[] getSelectItems(List<?> entidades, boolean selecionarPrimeiro) {
        int size = selecionarPrimeiro ? entidades.size() + 1 : entidades.size();
        SelectItem[] items = new SelectItem[size];
        int i = 0;
        if (selecionarPrimeiro) {
            items[0] = new SelectItem("", "---");
            i++;
        }
        for (Object x : entidades) {
            items[i++] = new SelectItem(x, x.toString());
        }
        return items;
    }

    public static boolean isValido() {
        return FacesContext.getCurrentInstance().isValidationFailed();
    }

    public static void addErroMsg(Exception ex, String defaultMsg) {
        String msg = ex.getLocalizedMessage();
        if (msg != null && msg.length() > 0) {
            addMsgErro(msg);
        } else {
            addMsgErro(defaultMsg);
        }
    }

    public static void addErrosMsgs(List<String> msgs) {
        for (String msg : msgs) {
            addMsgErro(msg);
        }
    }

    public static void addMsgErro(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }

    public static void addMsgSucesso(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg);
        FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);
    }

    public static String getParamRequest(String key) {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(key);
    }

    public static Object getObjectFromRequestParameter(String requestParameterName, Converter converter, UIComponent component) {
        String idParam = WebUtil.getParamRequest(requestParameterName);
        return converter.getAsObject(FacesContext.getCurrentInstance(), component, idParam);
    }
}
