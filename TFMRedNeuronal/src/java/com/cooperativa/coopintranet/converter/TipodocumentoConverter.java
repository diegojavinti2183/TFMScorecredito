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
@FacesConverter("tipodocumentoConverter")
public class TipodocumentoConverter implements Converter {
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        String tipodocumento = "";
        char valor;
        if (value != null) {
            valor = value.toString().charAt(0);
            switch (valor) {
                case ('R'):
                    tipodocumento = "Ruc";
                    break;
                case ('C'):
                    tipodocumento = "Cedula";
                    break;
                case ('P'):
                    tipodocumento = "Pasaporte";
                    break;
            }
        }
        return tipodocumento;
    }
}
