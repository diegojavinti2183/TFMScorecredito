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
@Table(name = "categoriasred")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Categoriasred.findAll", query = "SELECT c FROM Categoriasred c")
    , @NamedQuery(name = "Categoriasred.findByCredCodigo", query = "SELECT c FROM Categoriasred c WHERE c.credCodigo = :credCodigo")
    , @NamedQuery(name = "Categoriasred.findByCredId", query = "SELECT c FROM Categoriasred c WHERE c.credId = :credId")
    , @NamedQuery(name = "Categoriasred.findByCredNombre", query = "SELECT c FROM Categoriasred c WHERE c.credNombre = :credNombre")
    , @NamedQuery(name = "Categoriasred.findByCredEstado", query = "SELECT c FROM Categoriasred c WHERE c.credEstado = :credEstado")
    , @NamedQuery(name = "Categoriasred.findByCredAdmining", query = "SELECT c FROM Categoriasred c WHERE c.credAdmining = :credAdmining")
    , @NamedQuery(name = "Categoriasred.findByCredAdminfec1", query = "SELECT c FROM Categoriasred c WHERE c.credAdminfec1 = :credAdminfec1")
    , @NamedQuery(name = "Categoriasred.findByCredAdminact", query = "SELECT c FROM Categoriasred c WHERE c.credAdminact = :credAdminact")
    , @NamedQuery(name = "Categoriasred.findByCredAdminfec2", query = "SELECT c FROM Categoriasred c WHERE c.credAdminfec2 = :credAdminfec2")})
public class Categoriasred implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cred_codigo")
    private Integer credCodigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "cred_id")
    private String credId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "cred_nombre")
    private String credNombre;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cred_estado")
    private boolean credEstado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "cred_admining")
    private String credAdmining;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cred_adminfec1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date credAdminfec1;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "cred_adminact")
    private String credAdminact;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cred_adminfec2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date credAdminfec2;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vredCategoria")
    private Collection<Variablesred> variablesredCollection;

    public Categoriasred() {
    }

    public Categoriasred(Integer credCodigo) {
        this.credCodigo = credCodigo;
    }

    public Categoriasred(Integer credCodigo, String credId, String credNombre, boolean credEstado, String credAdmining, Date credAdminfec1, String credAdminact, Date credAdminfec2) {
        this.credCodigo = credCodigo;
        this.credId = credId;
        this.credNombre = credNombre;
        this.credEstado = credEstado;
        this.credAdmining = credAdmining;
        this.credAdminfec1 = credAdminfec1;
        this.credAdminact = credAdminact;
        this.credAdminfec2 = credAdminfec2;
    }

    public Integer getCredCodigo() {
        return credCodigo;
    }

    public void setCredCodigo(Integer credCodigo) {
        this.credCodigo = credCodigo;
    }

    public String getCredId() {
        return credId;
    }

    public void setCredId(String credId) {
        this.credId = credId;
    }

    public String getCredNombre() {
        return credNombre;
    }

    public void setCredNombre(String credNombre) {
        this.credNombre = credNombre;
    }

    public boolean getCredEstado() {
        return credEstado;
    }

    public void setCredEstado(boolean credEstado) {
        this.credEstado = credEstado;
    }

    public String getCredAdmining() {
        return credAdmining;
    }

    public void setCredAdmining(String credAdmining) {
        this.credAdmining = credAdmining;
    }

    public Date getCredAdminfec1() {
        return credAdminfec1;
    }

    public void setCredAdminfec1(Date credAdminfec1) {
        this.credAdminfec1 = credAdminfec1;
    }

    public String getCredAdminact() {
        return credAdminact;
    }

    public void setCredAdminact(String credAdminact) {
        this.credAdminact = credAdminact;
    }

    public Date getCredAdminfec2() {
        return credAdminfec2;
    }

    public void setCredAdminfec2(Date credAdminfec2) {
        this.credAdminfec2 = credAdminfec2;
    }

    @XmlTransient
    public Collection<Variablesred> getVariablesredCollection() {
        return variablesredCollection;
    }

    public void setVariablesredCollection(Collection<Variablesred> variablesredCollection) {
        this.variablesredCollection = variablesredCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (credCodigo != null ? credCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Categoriasred)) {
            return false;
        }
        Categoriasred other = (Categoriasred) object;
        if ((this.credCodigo == null && other.credCodigo != null) || (this.credCodigo != null && !this.credCodigo.equals(other.credCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return credNombre;
    }
    
}
