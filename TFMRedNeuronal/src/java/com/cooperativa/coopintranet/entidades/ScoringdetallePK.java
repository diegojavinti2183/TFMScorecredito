/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cooperativa.coopintranet.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author diego
 */
@Embeddable
public class ScoringdetallePK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "dscor_numero")
    private int dscorNumero;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dscor_scoring")
    private int dscorScoring;

    public ScoringdetallePK() {
    }

    public ScoringdetallePK(int dscorNumero, int dscorScoring) {
        this.dscorNumero = dscorNumero;
        this.dscorScoring = dscorScoring;
    }

    public int getDscorNumero() {
        return dscorNumero;
    }

    public void setDscorNumero(int dscorNumero) {
        this.dscorNumero = dscorNumero;
    }

    public int getDscorScoring() {
        return dscorScoring;
    }

    public void setDscorScoring(int dscorScoring) {
        this.dscorScoring = dscorScoring;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) dscorNumero;
        hash += (int) dscorScoring;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ScoringdetallePK)) {
            return false;
        }
        ScoringdetallePK other = (ScoringdetallePK) object;
        if (this.dscorNumero != other.dscorNumero) {
            return false;
        }
        if (this.dscorScoring != other.dscorScoring) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cooperativa.coopintranet.entidades.ScoringdetallePK[ dscorNumero=" + dscorNumero + ", dscorScoring=" + dscorScoring + " ]";
    }
    
}
