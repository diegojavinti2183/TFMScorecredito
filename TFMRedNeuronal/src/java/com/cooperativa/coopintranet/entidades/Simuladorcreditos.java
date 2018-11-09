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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "simuladorcreditos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Simuladorcreditos.findAll", query = "SELECT s FROM Simuladorcreditos s")
    , @NamedQuery(name = "Simuladorcreditos.findByScreNumero", query = "SELECT s FROM Simuladorcreditos s WHERE s.screNumero = :screNumero")
    , @NamedQuery(name = "Simuladorcreditos.findByScreFecha", query = "SELECT s FROM Simuladorcreditos s WHERE s.screFecha = :screFecha")
    , @NamedQuery(name = "Simuladorcreditos.findByScreTipocredito", query = "SELECT s FROM Simuladorcreditos s WHERE s.screTipocredito = :screTipocredito")
    , @NamedQuery(name = "Simuladorcreditos.findByScreMonto", query = "SELECT s FROM Simuladorcreditos s WHERE s.screMonto = :screMonto")
    , @NamedQuery(name = "Simuladorcreditos.findByScreTasa", query = "SELECT s FROM Simuladorcreditos s WHERE s.screTasa = :screTasa")
    , @NamedQuery(name = "Simuladorcreditos.findByScreProductoSocio", query = "SELECT s FROM Simuladorcreditos s WHERE s.screProductoSocio = :screProductoSocio")
    , @NamedQuery(name = "Simuladorcreditos.findByScreCapital", query = "SELECT s FROM Simuladorcreditos s WHERE s.screCapital = :screCapital")
    , @NamedQuery(name = "Simuladorcreditos.findByScreDesembolso", query = "SELECT s FROM Simuladorcreditos s WHERE s.screDesembolso = :screDesembolso")
    , @NamedQuery(name = "Simuladorcreditos.findByScreSaldo", query = "SELECT s FROM Simuladorcreditos s WHERE s.screSaldo = :screSaldo")
    , @NamedQuery(name = "Simuladorcreditos.findByScreInteres", query = "SELECT s FROM Simuladorcreditos s WHERE s.screInteres = :screInteres")
    , @NamedQuery(name = "Simuladorcreditos.findByScrePlazo", query = "SELECT s FROM Simuladorcreditos s WHERE s.screPlazo = :screPlazo")
    , @NamedQuery(name = "Simuladorcreditos.findByScreDiasAmortizacion", query = "SELECT s FROM Simuladorcreditos s WHERE s.screDiasAmortizacion = :screDiasAmortizacion")
    , @NamedQuery(name = "Simuladorcreditos.findByScreCuotas", query = "SELECT s FROM Simuladorcreditos s WHERE s.screCuotas = :screCuotas")
    , @NamedQuery(name = "Simuladorcreditos.findByScreDestino", query = "SELECT s FROM Simuladorcreditos s WHERE s.screDestino = :screDestino")
    , @NamedQuery(name = "Simuladorcreditos.findByScreAdmining", query = "SELECT s FROM Simuladorcreditos s WHERE s.screAdmining = :screAdmining")
    , @NamedQuery(name = "Simuladorcreditos.findByScreAdminfec1", query = "SELECT s FROM Simuladorcreditos s WHERE s.screAdminfec1 = :screAdminfec1")
    , @NamedQuery(name = "Simuladorcreditos.findByScreAdminact", query = "SELECT s FROM Simuladorcreditos s WHERE s.screAdminact = :screAdminact")
    , @NamedQuery(name = "Simuladorcreditos.findByScreAdminfec2", query = "SELECT s FROM Simuladorcreditos s WHERE s.screAdminfec2 = :screAdminfec2")})
public class Simuladorcreditos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "scre_numero")
    private Integer screNumero;
    @Basic(optional = false)
    @NotNull
    @Column(name = "scre_fecha")
    @Temporal(TemporalType.DATE)
    private Date screFecha;
    @Basic(optional = false)
    @NotNull
    @Column(name = "scre_tipocredito")
    private int screTipocredito;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "scre_monto")
    private BigDecimal screMonto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "scre_tasa")
    private BigDecimal screTasa;
    @Column(name = "scre_producto_socio")
    private Short screProductoSocio;
    @Column(name = "scre_capital")
    private BigDecimal screCapital;
    @Column(name = "scre_desembolso")
    private BigDecimal screDesembolso;
    @Column(name = "scre_saldo")
    private BigDecimal screSaldo;
    @Column(name = "scre_interes")
    private BigDecimal screInteres;
    @Column(name = "scre_plazo")
    private Integer screPlazo;
    @Column(name = "scre_dias_amortizacion")
    private Short screDiasAmortizacion;
    @Column(name = "scre_cuotas")
    private Short screCuotas;
    @Column(name = "scre_destino")
    private Short screDestino;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "scre_admining")
    private String screAdmining;
    @Basic(optional = false)
    @NotNull
    @Column(name = "scre_adminfec1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date screAdminfec1;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "scre_adminact")
    private String screAdminact;
    @Basic(optional = false)
    @NotNull
    @Column(name = "scre_adminfec2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date screAdminfec2;
    @JoinColumn(name = "scre_persona", referencedColumnName = "per_codigo")
    @ManyToOne(optional = false)
    private Personas screPersona;

    public Simuladorcreditos() {
    }

    public Simuladorcreditos(Integer screNumero) {
        this.screNumero = screNumero;
    }

    public Simuladorcreditos(Integer screNumero, Date screFecha, int screTipocredito, BigDecimal screMonto, BigDecimal screTasa, String screAdmining, Date screAdminfec1, String screAdminact, Date screAdminfec2) {
        this.screNumero = screNumero;
        this.screFecha = screFecha;
        this.screTipocredito = screTipocredito;
        this.screMonto = screMonto;
        this.screTasa = screTasa;
        this.screAdmining = screAdmining;
        this.screAdminfec1 = screAdminfec1;
        this.screAdminact = screAdminact;
        this.screAdminfec2 = screAdminfec2;
    }

    public Integer getScreNumero() {
        return screNumero;
    }

    public void setScreNumero(Integer screNumero) {
        this.screNumero = screNumero;
    }

    public Date getScreFecha() {
        return screFecha;
    }

    public void setScreFecha(Date screFecha) {
        this.screFecha = screFecha;
    }

    public int getScreTipocredito() {
        return screTipocredito;
    }

    public void setScreTipocredito(int screTipocredito) {
        this.screTipocredito = screTipocredito;
    }

    public BigDecimal getScreMonto() {
        return screMonto;
    }

    public void setScreMonto(BigDecimal screMonto) {
        this.screMonto = screMonto;
    }

    public BigDecimal getScreTasa() {
        return screTasa;
    }

    public void setScreTasa(BigDecimal screTasa) {
        this.screTasa = screTasa;
    }

    public Short getScreProductoSocio() {
        return screProductoSocio;
    }

    public void setScreProductoSocio(Short screProductoSocio) {
        this.screProductoSocio = screProductoSocio;
    }

    public BigDecimal getScreCapital() {
        return screCapital;
    }

    public void setScreCapital(BigDecimal screCapital) {
        this.screCapital = screCapital;
    }

    public BigDecimal getScreDesembolso() {
        return screDesembolso;
    }

    public void setScreDesembolso(BigDecimal screDesembolso) {
        this.screDesembolso = screDesembolso;
    }

    public BigDecimal getScreSaldo() {
        return screSaldo;
    }

    public void setScreSaldo(BigDecimal screSaldo) {
        this.screSaldo = screSaldo;
    }

    public BigDecimal getScreInteres() {
        return screInteres;
    }

    public void setScreInteres(BigDecimal screInteres) {
        this.screInteres = screInteres;
    }

    public Integer getScrePlazo() {
        return screPlazo;
    }

    public void setScrePlazo(Integer screPlazo) {
        this.screPlazo = screPlazo;
    }

    public Short getScreDiasAmortizacion() {
        return screDiasAmortizacion;
    }

    public void setScreDiasAmortizacion(Short screDiasAmortizacion) {
        this.screDiasAmortizacion = screDiasAmortizacion;
    }

    public Short getScreCuotas() {
        return screCuotas;
    }

    public void setScreCuotas(Short screCuotas) {
        this.screCuotas = screCuotas;
    }

    public Short getScreDestino() {
        return screDestino;
    }

    public void setScreDestino(Short screDestino) {
        this.screDestino = screDestino;
    }

    public String getScreAdmining() {
        return screAdmining;
    }

    public void setScreAdmining(String screAdmining) {
        this.screAdmining = screAdmining;
    }

    public Date getScreAdminfec1() {
        return screAdminfec1;
    }

    public void setScreAdminfec1(Date screAdminfec1) {
        this.screAdminfec1 = screAdminfec1;
    }

    public String getScreAdminact() {
        return screAdminact;
    }

    public void setScreAdminact(String screAdminact) {
        this.screAdminact = screAdminact;
    }

    public Date getScreAdminfec2() {
        return screAdminfec2;
    }

    public void setScreAdminfec2(Date screAdminfec2) {
        this.screAdminfec2 = screAdminfec2;
    }

    public Personas getScrePersona() {
        return screPersona;
    }

    public void setScrePersona(Personas screPersona) {
        this.screPersona = screPersona;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (screNumero != null ? screNumero.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Simuladorcreditos)) {
            return false;
        }
        Simuladorcreditos other = (Simuladorcreditos) object;
        if ((this.screNumero == null && other.screNumero != null) || (this.screNumero != null && !this.screNumero.equals(other.screNumero))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cooperativa.coopintranet.entidades.Simuladorcreditos[ screNumero=" + screNumero + " ]";
    }
    
}
