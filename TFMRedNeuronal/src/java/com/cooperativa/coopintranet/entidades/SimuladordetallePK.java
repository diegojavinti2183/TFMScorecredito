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
public class SimuladordetallePK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "dscre_numero")
    private int dscreNumero;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dscre_simulador")
    private int dscreSimulador;

    public SimuladordetallePK() {
    }

    public SimuladordetallePK(int dscreNumero, int dscreSimulador) {
        this.dscreNumero = dscreNumero;
        this.dscreSimulador = dscreSimulador;
    }

    public int getDscreNumero() {
        return dscreNumero;
    }

    public void setDscreNumero(int dscreNumero) {
        this.dscreNumero = dscreNumero;
    }

    public int getDscreSimulador() {
        return dscreSimulador;
    }

    public void setDscreSimulador(int dscreSimulador) {
        this.dscreSimulador = dscreSimulador;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) dscreNumero;
        hash += (int) dscreSimulador;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SimuladordetallePK)) {
            return false;
        }
        SimuladordetallePK other = (SimuladordetallePK) object;
        if (this.dscreNumero != other.dscreNumero) {
            return false;
        }
        if (this.dscreSimulador != other.dscreSimulador) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cooperativa.coopintranet.entidades.SimuladordetallePK[ dscreNumero=" + dscreNumero + ", dscreSimulador=" + dscreSimulador + " ]";
    }
    
}
