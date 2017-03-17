package com.softplan.model.util;

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
}
