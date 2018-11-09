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
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author diego
 */
@Entity
@Table(name = "redneuronal")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Redneuronal.findAll", query = "SELECT r FROM Redneuronal r")
    , @NamedQuery(name = "Redneuronal.findByRedSecuencia", query = "SELECT r FROM Redneuronal r WHERE r.redSecuencia = :redSecuencia")
    , @NamedQuery(name = "Redneuronal.findByRedNentrada", query = "SELECT r FROM Redneuronal r WHERE r.redNentrada = :redNentrada")
    , @NamedQuery(name = "Redneuronal.findByRedNoculta", query = "SELECT r FROM Redneuronal r WHERE r.redNoculta = :redNoculta")
    , @NamedQuery(name = "Redneuronal.findByRedNcapasocultas", query = "SELECT r FROM Redneuronal r WHERE r.redNcapasocultas = :redNcapasocultas")
    , @NamedQuery(name = "Redneuronal.findByRedNsalida", query = "SELECT r FROM Redneuronal r WHERE r.redNsalida = :redNsalida")
    , @NamedQuery(name = "Redneuronal.findByRedIteraciones", query = "SELECT r FROM Redneuronal r WHERE r.redIteraciones = :redIteraciones")
    , @NamedQuery(name = "Redneuronal.findByRedMaxerror", query = "SELECT r FROM Redneuronal r WHERE r.redMaxerror = :redMaxerror")
    , @NamedQuery(name = "Redneuronal.updateRedEstado", query = "UPDATE Redneuronal r SET r.redEstado = false WHERE r.redEstado = :redEstado")
    , @NamedQuery(name = "Redneuronal.findByRedEstado", query = "SELECT r FROM Redneuronal r WHERE r.redEstado = :redEstado")
    , @NamedQuery(name = "Redneuronal.findByRedAdmining", query = "SELECT r FROM Redneuronal r WHERE r.redAdmining = :redAdmining")
    , @NamedQuery(name = "Redneuronal.findByRedAdminfec1", query = "SELECT r FROM Redneuronal r WHERE r.redAdminfec1 = :redAdminfec1")
    , @NamedQuery(name = "Redneuronal.findByRedAdminact", query = "SELECT r FROM Redneuronal r WHERE r.redAdminact = :redAdminact")
    , @NamedQuery(name = "Redneuronal.findByRedAdminfec2", query = "SELECT r FROM Redneuronal r WHERE r.redAdminfec2 = :redAdminfec2")})
public class Redneuronal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "red_secuencia")
    private Integer redSecuencia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "red_nentrada")
    private int redNentrada;
    @Basic(optional = false)
    @NotNull
    @Column(name = "red_noculta")
    private int redNoculta;
    @Basic(optional = false)
    @NotNull
    @Column(name = "red_ncapasocultas")
    private int redNcapasocultas;
    @Basic(optional = false)
    @NotNull
    @Column(name = "red_nsalida")
    private int redNsalida;
    @Basic(optional = false)
    @NotNull
    @Column(name = "red_iteraciones")
    private int redIteraciones;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "red_maxerror")
    private BigDecimal redMaxerror;
    @Basic(optional = false)
    @NotNull
    @Column(name = "red_estado")
    private boolean redEstado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "red_admining")
    private String redAdmining;
    @Basic(optional = false)
    @NotNull
    @Column(name = "red_adminfec1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date redAdminfec1;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "red_adminact")
    private String redAdminact;
    @Basic(optional = false)
    @NotNull
    @Column(name = "red_adminfec2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date redAdminfec2;

    public Redneuronal() {
    }

    public Redneuronal(Integer redSecuencia) {
        this.redSecuencia = redSecuencia;
    }

    public Redneuronal(Integer redSecuencia, int redNentrada, int redNoculta, int redNcapasocultas, int redNsalida, int redIteraciones, BigDecimal redMaxerror, boolean redEstado, String redAdmining, Date redAdminfec1, String redAdminact, Date redAdminfec2) {
        this.redSecuencia = redSecuencia;
        this.redNentrada = redNentrada;
        this.redNoculta = redNoculta;
        this.redNcapasocultas = redNcapasocultas;
        this.redNsalida = redNsalida;
        this.redIteraciones = redIteraciones;
        this.redMaxerror = redMaxerror;
        this.redEstado = redEstado;
        this.redAdmining = redAdmining;
        this.redAdminfec1 = redAdminfec1;
        this.redAdminact = redAdminact;
        this.redAdminfec2 = redAdminfec2;
    }

    public Integer getRedSecuencia() {
        return redSecuencia;
    }

    public void setRedSecuencia(Integer redSecuencia) {
        this.redSecuencia = redSecuencia;
    }

    public int getRedNentrada() {
        return redNentrada;
    }

    public void setRedNentrada(int redNentrada) {
        this.redNentrada = redNentrada;
    }

    public int getRedNoculta() {
        return redNoculta;
    }

    public void setRedNoculta(int redNoculta) {
        this.redNoculta = redNoculta;
    }

    public int getRedNcapasocultas() {
        return redNcapasocultas;
    }

    public void setRedNcapasocultas(int redNcapasocultas) {
        this.redNcapasocultas = redNcapasocultas;
    }

    public int getRedNsalida() {
        return redNsalida;
    }

    public void setRedNsalida(int redNsalida) {
        this.redNsalida = redNsalida;
    }

    public int getRedIteraciones() {
        return redIteraciones;
    }

    public void setRedIteraciones(int redIteraciones) {
        this.redIteraciones = redIteraciones;
    }

    public BigDecimal getRedMaxerror() {
        return redMaxerror;
    }

    public void setRedMaxerror(BigDecimal redMaxerror) {
        this.redMaxerror = redMaxerror;
    }

    public boolean getRedEstado() {
        return redEstado;
    }

    public void setRedEstado(boolean redEstado) {
        this.redEstado = redEstado;
    }

    public String getRedAdmining() {
        return redAdmining;
    }

    public void setRedAdmining(String redAdmining) {
        this.redAdmining = redAdmining;
    }

    public Date getRedAdminfec1() {
        return redAdminfec1;
    }

    public void setRedAdminfec1(Date redAdminfec1) {
        this.redAdminfec1 = redAdminfec1;
    }

    public String getRedAdminact() {
        return redAdminact;
    }

    public void setRedAdminact(String redAdminact) {
        this.redAdminact = redAdminact;
    }

    public Date getRedAdminfec2() {
        return redAdminfec2;
    }

    public void setRedAdminfec2(Date redAdminfec2) {
        this.redAdminfec2 = redAdminfec2;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (redSecuencia != null ? redSecuencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Redneuronal)) {
            return false;
        }
        Redneuronal other = (Redneuronal) object;
        if ((this.redSecuencia == null && other.redSecuencia != null) || (this.redSecuencia != null && !this.redSecuencia.equals(other.redSecuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cooperativa.coopintranet.entidades.Redneuronal[ redSecuencia=" + redSecuencia + " ]";
    }

}
