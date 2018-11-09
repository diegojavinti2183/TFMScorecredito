/*
 * To change this template, choose Tools | Templates
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
 * @author Diego Ruiz
 */
@Entity
@Table(name = "paginas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Paginas.findAll", query = "SELECT p FROM Paginas p"),
    @NamedQuery(name = "Paginas.findByPagCodigo", query = "SELECT p FROM Paginas p WHERE p.pagCodigo = :pagCodigo"),
    @NamedQuery(name = "Paginas.findByPagNombre", query = "SELECT p FROM Paginas p WHERE p.pagNombre = :pagNombre"),
    @NamedQuery(name = "Paginas.findByPagRuta", query = "SELECT p FROM Paginas p WHERE p.pagRuta = :pagRuta"),
    @NamedQuery(name = "Paginas.findByPagActivo", query = "SELECT p FROM Paginas p WHERE p.pagActivo = :pagActivo"),
    @NamedQuery(name = "Paginas.findByPagAdmining", query = "SELECT p FROM Paginas p WHERE p.pagAdmining = :pagAdmining"),
    @NamedQuery(name = "Paginas.findByPagAdminfec1", query = "SELECT p FROM Paginas p WHERE p.pagAdminfec1 = :pagAdminfec1"),
    @NamedQuery(name = "Paginas.findByPagAdminact", query = "SELECT p FROM Paginas p WHERE p.pagAdminact = :pagAdminact"),
    @NamedQuery(name = "Paginas.findByPagAdminfec2", query = "SELECT p FROM Paginas p WHERE p.pagAdminfec2 = :pagAdminfec2")})
public class Paginas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pag_codigo")
    private Integer pagCodigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "pag_nombre")
    private String pagNombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "pag_ruta")
    private String pagRuta;
    @Column(name = "pag_activo")
    private Boolean pagActivo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "pag_admining")
    private String pagAdmining;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pag_adminfec1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date pagAdminfec1;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "pag_adminact")
    private String pagAdminact;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pag_adminfec2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date pagAdminfec2;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rolpPagina")
    private Collection<Rolpaginas> rolpaginasCollection;

    public Paginas() {
    }

    public Paginas(Integer pagCodigo) {
        this.pagCodigo = pagCodigo;
    }

    public Paginas(Integer pagCodigo, String pagNombre, String pagRuta, String pagAdmining, Date pagAdminfec1, String pagAdminact, Date pagAdminfec2) {
        this.pagCodigo = pagCodigo;
        this.pagNombre = pagNombre;
        this.pagRuta = pagRuta;
        this.pagAdmining = pagAdmining;
        this.pagAdminfec1 = pagAdminfec1;
        this.pagAdminact = pagAdminact;
        this.pagAdminfec2 = pagAdminfec2;
    }

    public Integer getPagCodigo() {
        return pagCodigo;
    }

    public void setPagCodigo(Integer pagCodigo) {
        this.pagCodigo = pagCodigo;
    }

    public String getPagNombre() {
        return pagNombre;
    }

    public void setPagNombre(String pagNombre) {
        this.pagNombre = pagNombre;
    }

    public String getPagRuta() {
        return pagRuta;
    }

    public void setPagRuta(String pagRuta) {
        this.pagRuta = pagRuta;
    }

    public Boolean getPagActivo() {
        return pagActivo;
    }

    public void setPagActivo(Boolean pagActivo) {
        this.pagActivo = pagActivo;
    }

    public String getPagAdmining() {
        return pagAdmining;
    }

    public void setPagAdmining(String pagAdmining) {
        this.pagAdmining = pagAdmining;
    }

    public Date getPagAdminfec1() {
        return pagAdminfec1;
    }

    public void setPagAdminfec1(Date pagAdminfec1) {
        this.pagAdminfec1 = pagAdminfec1;
    }

    public String getPagAdminact() {
        return pagAdminact;
    }

    public void setPagAdminact(String pagAdminact) {
        this.pagAdminact = pagAdminact;
    }

    public Date getPagAdminfec2() {
        return pagAdminfec2;
    }

    public void setPagAdminfec2(Date pagAdminfec2) {
        this.pagAdminfec2 = pagAdminfec2;
    }

    @XmlTransient
    public Collection<Rolpaginas> getRolpaginasCollection() {
        return rolpaginasCollection;
    }

    public void setRolpaginasCollection(Collection<Rolpaginas> rolpaginasCollection) {
        this.rolpaginasCollection = rolpaginasCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pagCodigo != null ? pagCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Paginas)) {
            return false;
        }
        Paginas other = (Paginas) object;
        if ((this.pagCodigo == null && other.pagCodigo != null) || (this.pagCodigo != null && !this.pagCodigo.equals(other.pagCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return pagNombre;
    }
    
}
