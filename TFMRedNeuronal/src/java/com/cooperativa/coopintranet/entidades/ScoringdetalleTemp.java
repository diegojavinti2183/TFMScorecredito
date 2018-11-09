/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cooperativa.coopintranet.entidades;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 *
 * @author diego
 */

public class ScoringdetalleTemp implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Integer numero;    
    private BigDecimal dscorValor;
    private BigDecimal dscorHomologacion;
    private Variablesred dscorVariable;

    public ScoringdetalleTemp() {
    }

     public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public BigDecimal getDscorValor() {
        return dscorValor;
    }

    public void setDscorValor(BigDecimal dscorValor) {
        this.dscorValor = dscorValor;
    }

    public BigDecimal getDscorHomologacion() {
        return dscorHomologacion;
    }

    public void setDscorHomologacion(BigDecimal dscorHomologacion) {
        this.dscorHomologacion = dscorHomologacion;
    }

    public Variablesred getDscorVariable() {
        return dscorVariable;
    }

    public void setDscorVariable(Variablesred dscorVariable) {
        this.dscorVariable = dscorVariable;
    }

    

    @Override
    public String toString() {
        return dscorVariable.getVredDescripcion();
    }

   
    
}
