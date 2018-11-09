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
@Table(name = "variablesred")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Variablesred.findAll", query = "SELECT v FROM Variablesred v")
    , @NamedQuery(name = "Variablesred.findByVredCodigo", query = "SELECT v FROM Variablesred v WHERE v.vredCodigo = :vredCodigo")
    , @NamedQuery(name = "Variablesred.findByVredId", query = "SELECT v FROM Variablesred v WHERE v.vredId = :vredId")
    , @NamedQuery(name = "Variablesred.findByVredNombre", query = "SELECT v FROM Variablesred v WHERE v.vredNombre = :vredNombre")
    , @NamedQuery(name = "Variablesred.findByVredDescripcion", query = "SELECT v FROM Variablesred v WHERE v.vredDescripcion = :vredDescripcion")
    , @NamedQuery(name = "Variablesred.findByVredTipodato", query = "SELECT v FROM Variablesred v WHERE v.vredTipodato = :vredTipodato")
    , @NamedQuery(name = "Variablesred.findByVredEstado", query = "SELECT v FROM Variablesred v WHERE v.vredEstado = :vredEstado")
    , @NamedQuery(name = "Variablesred.findByVredAdmining", query = "SELECT v FROM Variablesred v WHERE v.vredAdmining = :vredAdmining")
    , @NamedQuery(name = "Variablesred.findByVredAdminfec1", query = "SELECT v FROM Variablesred v WHERE v.vredAdminfec1 = :vredAdminfec1")
    , @NamedQuery(name = "Variablesred.findByVredAdminact", query = "SELECT v FROM Variablesred v WHERE v.vredAdminact = :vredAdminact")
    , @NamedQuery(name = "Variablesred.findByVredAdminfec2", query = "SELECT v FROM Variablesred v WHERE v.vredAdminfec2 = :vredAdminfec2")})
public class Variablesred implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "vred_codigo")
    private Integer vredCodigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "vred_id")
    private String vredId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "vred_nombre")
    private String vredNombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "vred_descripcion")
    private String vredDescripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "vred_tipodato")
    private Character vredTipodato;
    @Basic(optional = false)
    @NotNull
    @Column(name = "vred_estado")
    private boolean vredEstado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "vred_admining")
    private String vredAdmining;
    @Basic(optional = false)
    @NotNull
    @Column(name = "vred_adminfec1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date vredAdminfec1;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "vred_adminact")
    private String vredAdminact;
    @Basic(optional = false)
    @NotNull
    @Column(name = "vred_adminfec2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date vredAdminfec2;
    @JoinColumn(name = "vred_categoria", referencedColumnName = "cred_codigo")
    @ManyToOne(optional = false)
    private Categoriasred vredCategoria;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dscorVariable")
    private Collection<Scoringdetalle> scoringdetalleCollection;

    public Variablesred() {
    }

    public Variablesred(Integer vredCodigo) {
        this.vredCodigo = vredCodigo;
    }

    public Variablesred(Integer vredCodigo, String vredId, String vredNombre, String vredDescripcion, Character vredTipodato, boolean vredEstado, String vredAdmining, Date vredAdminfec1, String vredAdminact, Date vredAdminfec2) {
        this.vredCodigo = vredCodigo;
        this.vredId = vredId;
        this.vredNombre = vredNombre;
        this.vredDescripcion = vredDescripcion;
        this.vredTipodato = vredTipodato;
        this.vredEstado = vredEstado;
        this.vredAdmining = vredAdmining;
        this.vredAdminfec1 = vredAdminfec1;
        this.vredAdminact = vredAdminact;
        this.vredAdminfec2 = vredAdminfec2;
    }

    public Integer getVredCodigo() {
        return vredCodigo;
    }

    public void setVredCodigo(Integer vredCodigo) {
        this.vredCodigo = vredCodigo;
    }

    public String getVredId() {
        return vredId;
    }

    public void setVredId(String vredId) {
        this.vredId = vredId;
    }

    public String getVredNombre() {
        return vredNombre;
    }

    public void setVredNombre(String vredNombre) {
        this.vredNombre = vredNombre;
    }

    public String getVredDescripcion() {
        return vredDescripcion;
    }

    public void setVredDescripcion(String vredDescripcion) {
        this.vredDescripcion = vredDescripcion;
    }

    public Character getVredTipodato() {
        return vredTipodato;
    }

    public void setVredTipodato(Character vredTipodato) {
        this.vredTipodato = vredTipodato;
    }

    public boolean getVredEstado() {
        return vredEstado;
    }

    public void setVredEstado(boolean vredEstado) {
        this.vredEstado = vredEstado;
    }

    public String getVredAdmining() {
        return vredAdmining;
    }

    public void setVredAdmining(String vredAdmining) {
        this.vredAdmining = vredAdmining;
    }

    public Date getVredAdminfec1() {
        return vredAdminfec1;
    }

    public void setVredAdminfec1(Date vredAdminfec1) {
        this.vredAdminfec1 = vredAdminfec1;
    }

    public String getVredAdminact() {
        return vredAdminact;
    }

    public void setVredAdminact(String vredAdminact) {
        this.vredAdminact = vredAdminact;
    }

    public Date getVredAdminfec2() {
        return vredAdminfec2;
    }

    public void setVredAdminfec2(Date vredAdminfec2) {
        this.vredAdminfec2 = vredAdminfec2;
    }

    public Categoriasred getVredCategoria() {
        return vredCategoria;
    }

    public void setVredCategoria(Categoriasred vredCategoria) {
        this.vredCategoria = vredCategoria;
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
        hash += (vredCodigo != null ? vredCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Variablesred)) {
            return false;
        }
        Variablesred other = (Variablesred) object;
        if ((this.vredCodigo == null && other.vredCodigo != null) || (this.vredCodigo != null && !this.vredCodigo.equals(other.vredCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cooperativa.coopintranet.entidades.Variablesred[ vredCodigo=" + vredCodigo + " ]";
    }
    
}
