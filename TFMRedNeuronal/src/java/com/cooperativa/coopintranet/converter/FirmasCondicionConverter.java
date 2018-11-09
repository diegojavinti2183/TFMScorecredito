/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cooperativa.coopintranet.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Diego Ruiz
 */
@FacesConverter("firmasCondicionConverter")
public class FirmasCondicionConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        String codicion = "";
        String valor;
        if (value != null) {
            valor = value.toString();
            if (valor.equals("1")) {
                codicion = "CONJUNTAS";
            } else if (valor.equals("2")) {
                codicion = "INDISTINTAS";
            } else if (valor.equals("3")) {
                codicion = "INDIVIDUAL";
            } else if (valor.equals("4")) {
                codicion = "REPRESENTANTE";
            } 
        }
        return codicion;
    }

}
