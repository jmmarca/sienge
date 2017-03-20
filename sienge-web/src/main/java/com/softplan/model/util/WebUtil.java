package com.softplan.model.util;

import com.softplan.model.entidade.Usuario;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class WebUtil {

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

    public static Object getSessionObj(String chave) {
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(chave);
    }

    public static void setSessionObj(String id, Object obj) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(id, obj);
    }

    public static Usuario getUsuario() {
        return (Usuario) getSessionObj("usuario");
    }
}
