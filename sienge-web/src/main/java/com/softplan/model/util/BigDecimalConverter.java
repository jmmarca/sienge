/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softplan.model.util;

import java.math.BigDecimal;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass = BigDecimal.class, value = "bigDecimalConverter")
public class BigDecimalConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
        BigDecimal valor = null;
        if (arg2 != null) {
            if (arg2.isEmpty() == false) {
                valor = new BigDecimal(arg2);
            }
        }
        return valor;
    }

    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
        return arg2.toString();
    }
}
