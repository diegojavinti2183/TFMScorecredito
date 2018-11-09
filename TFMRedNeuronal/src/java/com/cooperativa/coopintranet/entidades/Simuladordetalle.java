/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cooperativa.coopintranet.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author diego
 */
@Entity
@Table(name = "simuladordetalle")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Simuladordetalle.findAll", query = "SELECT s FROM Simuladordetalle s")
    , @NamedQuery(name = "Simuladordetalle.findByDscreNumero", query = "SELECT s FROM Simuladordetalle s WHERE s.simuladordetallePK.dscreNumero = :dscreNumero")
    , @NamedQuery(name = "Simuladordetalle.findByDscreSimulador", query = "SELECT s FROM Simuladordetalle s WHERE s.simuladordetallePK.dscreSimulador = :dscreSimulador")
    , @NamedQuery(name = "Simuladordetalle.findByDscreCapital", query = "SELECT s FROM Simuladordetalle s WHERE s.dscreCapital = :dscreCapital")
    , @NamedQuery(name = "Simuladordetalle.findByDscreInteres", query = "SELECT s FROM Simuladordetalle s WHERE s.dscreInteres = :dscreInteres")
    , @NamedQuery(name = "Simuladordetalle.findByDscreFechainicio", query = "SELECT s FROM Simuladordetalle s WHERE s.dscreFechainicio = :dscreFechainicio")
    , @NamedQuery(name = "Simuladordetalle.findByDscreFechafin", query = "SELECT s FROM Simuladordetalle s WHERE s.dscreFechafin = :dscreFechafin")
    , @NamedQuery(name = "Simuladordetalle.findByDscreSeguro", query = "SELECT s FROM Simuladordetalle s WHERE s.dscreSeguro = :dscreSeguro")
    , @NamedQuery(name = "Simuladordetalle.findByDscreValorcuota", query = "SELECT s FROM Simuladordetalle s WHERE s.dscreValorcuota = :dscreValorcuota")})
public class Simuladordetalle implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SimuladordetallePK simuladordetallePK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "dscre_capital")
    private BigDecimal dscreCapital;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dscre_interes")
    private BigDecimal dscreInteres;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dscre_fechainicio")
    @Temporal(TemporalType.DATE)
    private Date dscreFechainicio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dscre_fechafin")
    @Temporal(TemporalType.DATE)
    private Date dscreFechafin;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dscre_seguro")
    private BigDecimal dscreSeguro;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dscre_valorcuota")
    private BigDecimal dscreValorcuota;

    public Simuladordetalle() {
    }

    public Simuladordetalle(SimuladordetallePK simuladordetallePK) {
        this.simuladordetallePK = simuladordetallePK;
    }

    public Simuladordetalle(SimuladordetallePK simuladordetallePK, BigDecimal dscreCapital, BigDecimal dscreInteres, Date dscreFechainicio, Date dscreFechafin, BigDecimal dscreSeguro, BigDecimal dscreValorcuota) {
        this.simuladordetallePK = simuladordetallePK;
        this.dscreCapital = dscreCapital;
        this.dscreInteres = dscreInteres;
        this.dscreFechainicio = dscreFechainicio;
        this.dscreFechafin = dscreFechafin;
        this.dscreSeguro = dscreSeguro;
        this.dscreValorcuota = dscreValorcuota;
    }

    public Simuladordetalle(int dscreNumero, int dscreSimulador) {
        this.simuladordetallePK = new SimuladordetallePK(dscreNumero, dscreSimulador);
    }

    public SimuladordetallePK getSimuladordetallePK() {
        return simuladordetallePK;
    }

    public void setSimuladordetallePK(SimuladordetallePK simuladordetallePK) {
        this.simuladordetallePK = simuladordetallePK;
    }

    public BigDecimal getDscreCapital() {
        return dscreCapital;
    }

    public void setDscreCapital(BigDecimal dscreCapital) {
        this.dscreCapital = dscreCapital;
    }

    public BigDecimal getDscreInteres() {
        return dscreInteres;
    }

    public void setDscreInteres(BigDecimal dscreInteres) {
        this.dscreInteres = dscreInteres;
    }

    public Date getDscreFechainicio() {
        return dscreFechainicio;
    }

    public void setDscreFechainicio(Date dscreFechainicio) {
        this.dscreFechainicio = dscreFechainicio;
    }

    public Date getDscreFechafin() {
        return dscreFechafin;
    }

    public void setDscreFechafin(Date dscreFechafin) {
        this.dscreFechafin = dscreFechafin;
    }

    public BigDecimal getDscreSeguro() {
        return dscreSeguro;
    }

    public void setDscreSeguro(BigDecimal dscreSeguro) {
        this.dscreSeguro = dscreSeguro;
    }

    public BigDecimal getDscreValorcuota() {
        return dscreValorcuota;
    }

    public void setDscreValorcuota(BigDecimal dscreValorcuota) {
        this.dscreValorcuota = dscreValorcuota;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (simuladordetallePK != null ? simuladordetallePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Simuladordetalle)) {
            return false;
        }
        Simuladordetalle other = (Simuladordetalle) object;
        if ((this.simuladordetallePK == null && other.simuladordetallePK != null) || (this.simuladordetallePK != null && !this.simuladordetallePK.equals(other.simuladordetallePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cooperativa.coopintranet.entidades.Simuladordetalle[ simuladordetallePK=" + simuladordetallePK + " ]";
    }
    
}
