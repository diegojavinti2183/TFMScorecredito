/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cooperativa.coopintranet.entidades;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author diego
 */
@Entity
@Table(name = "scoringpersonas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Scoringpersonas.findAll", query = "SELECT s FROM Scoringpersonas s")
    , @NamedQuery(name = "Scoringpersonas.findByScorNumero", query = "SELECT s FROM Scoringpersonas s WHERE s.scorNumero = :scorNumero")
    , @NamedQuery(name = "Scoringpersonas.findByScorFecha", query = "SELECT s FROM Scoringpersonas s WHERE s.scorFecha = :scorFecha")
    , @NamedQuery(name = "Scoringpersonas.findByScorUsuario", query = "SELECT s FROM Scoringpersonas s WHERE s.scorUsuario = :scorUsuario")
    , @NamedQuery(name = "Scoringpersonas.findByScorCalificacion", query = "SELECT s FROM Scoringpersonas s WHERE s.scorCalificacion = :scorCalificacion")
    , @NamedQuery(name = "Scoringpersonas.findByScorAdmining", query = "SELECT s FROM Scoringpersonas s WHERE s.scorAdmining = :scorAdmining")
    , @NamedQuery(name = "Scoringpersonas.findByScorAdminfec1", query = "SELECT s FROM Scoringpersonas s WHERE s.scorAdminfec1 = :scorAdminfec1")
    , @NamedQuery(name = "Scoringpersonas.findByScorAdminact", query = "SELECT s FROM Scoringpersonas s WHERE s.scorAdminact = :scorAdminact")
    , @NamedQuery(name = "Scoringpersonas.findByScorAdminfec2", query = "SELECT s FROM Scoringpersonas s WHERE s.scorAdminfec2 = :scorAdminfec2")})
public class Scoringpersonas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "scor_numero")
    private Integer scorNumero;
    @Basic(optional = false)
    @NotNull
    @Column(name = "scor_fecha")
    @Temporal(TemporalType.DATE)
    private Date scorFecha;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "scor_usuario")
    private String scorUsuario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "scor_calificacion")
    private String scorCalificacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "scor_admining")
    private String scorAdmining;
    @Basic(optional = false)
    @NotNull
    @Column(name = "scor_adminfec1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date scorAdminfec1;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "scor_adminact")
    private String scorAdminact;
    @Basic(optional = false)
    @NotNull
    @Column(name = "scor_adminfec2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date scorAdminfec2;
    @JoinColumn(name = "scor_persona", referencedColumnName = "per_codigo")
    @ManyToOne(optional = false)
    private Personas scorPersona;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "scoringpersonas")
    private Collection<Scoringdetalle> scoringdetalleCollection;

    public Scoringpersonas() {
    }

    public Scoringpersonas(Integer scorNumero) {
        this.scorNumero = scorNumero;
    }

    public Scoringpersonas(Integer scorNumero, Date scorFecha, String scorUsuario, String scorCalificacion, String scorAdmining, Date scorAdminfec1, String scorAdminact, Date scorAdminfec2) {
        this.scorNumero = scorNumero;
        this.scorFecha = scorFecha;
        this.scorUsuario = scorUsuario;
        this.scorCalificacion = scorCalificacion;
        this.scorAdmining = scorAdmining;
        this.scorAdminfec1 = scorAdminfec1;
        this.scorAdminact = scorAdminact;
        this.scorAdminfec2 = scorAdminfec2;
    }

    public Integer getScorNumero() {
        return scorNumero;
    }

    public void setScorNumero(Integer scorNumero) {
        this.scorNumero = scorNumero;
    }

    public Date getScorFecha() {
        return scorFecha;
    }

    public void setScorFecha(Date scorFecha) {
        this.scorFecha = scorFecha;
    }

    public String getScorUsuario() {
        return scorUsuario;
    }

    public void setScorUsuario(String scorUsuario) {
        this.scorUsuario = scorUsuario;
    }

    public String getScorCalificacion() {
        return scorCalificacion;
    }

    public void setScorCalificacion(String scorCalificacion) {
        this.scorCalificacion = scorCalificacion;
    }

    public String getScorAdmining() {
        return scorAdmining;
    }

    public void setScorAdmining(String scorAdmining) {
        this.scorAdmining = scorAdmining;
    }

    public Date getScorAdminfec1() {
        return scorAdminfec1;
    }

    public void setScorAdminfec1(Date scorAdminfec1) {
        this.scorAdminfec1 = scorAdminfec1;
    }

    public String getScorAdminact() {
        return scorAdminact;
    }

    public void setScorAdminact(String scorAdminact) {
        this.scorAdminact = scorAdminact;
    }

    public Date getScorAdminfec2() {
        return scorAdminfec2;
    }

    public void setScorAdminfec2(Date scorAdminfec2) {
        this.scorAdminfec2 = scorAdminfec2;
    }

    public Personas getScorPersona() {
        return scorPersona;
    }

    public void setScorPersona(Personas scorPersona) {
        this.scorPersona = scorPersona;
    }

    @XmlTransient
    public Collection<Scoringdetalle> getScoringdetalleCollection() {
        return scoringdetalleCollection;
    }

    public void setScoringdetalleCollection(Collection<Scoringdetalle> scoringdetalleCollection) {
        this.scoringdetalleCollection = scoringdetalleCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (scorNumero != null ? scorNumero.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Scoringpersonas)) {
            return false;
        }
        Scoringpersonas other = (Scoringpersonas) object;
        if ((this.scorNumero == null && other.scorNumero != null) || (this.scorNumero != null && !this.scorNumero.equals(other.scorNumero))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cooperativa.coopintranet.entidades.Scoringpersonas[ scorNumero=" + scorNumero + " ]";
    }
    
}
