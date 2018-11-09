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
@FacesConverter("relacionConverter")
public class RalacionConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        String estudios = "";
        String valor;
        if (value != null) {
            valor = value.toString();
            if (valor.equals("DE")) {
                estudios = "DEUDOR";
            } else if (valor.equals("CO")) {
                estudios = "CODEUDOR";
            } else if (valor.equals("G1")) {
                estudios = "GARANTE 1";
            } else if (valor.equals("G2")) {
                estudios = "GARANTE 2";
            } else if (valor.equals("RF")) {
                estudios = "REFERENCIA FAMILIAR";
            } else if (valor.equals("RP")) {
                estudios = "REFERENCIA PERSONAL";
            }
        }
        return estudios;
    }

}
