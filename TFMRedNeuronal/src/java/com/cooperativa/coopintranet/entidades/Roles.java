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
@Table(name = "roles")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Roles.findAll", query = "SELECT r FROM Roles r"),
    @NamedQuery(name = "Roles.findByRolCodigo", query = "SELECT r FROM Roles r WHERE r.rolCodigo = :rolCodigo"),
    @NamedQuery(name = "Roles.findByRolNombre", query = "SELECT r FROM Roles r WHERE r.rolNombre = :rolNombre"),
    @NamedQuery(name = "Roles.findByRolActivo", query = "SELECT r FROM Roles r WHERE r.rolActivo = :rolActivo"),
    @NamedQuery(name = "Roles.findByRolAdmining", query = "SELECT r FROM Roles r WHERE r.rolAdmining = :rolAdmining"),
    @NamedQuery(name = "Roles.findByRolAdminfec1", query = "SELECT r FROM Roles r WHERE r.rolAdminfec1 = :rolAdminfec1"),
    @NamedQuery(name = "Roles.findByRolAdminact", query = "SELECT r FROM Roles r WHERE r.rolAdminact = :rolAdminact"),
    @NamedQuery(name = "Roles.findByRolAdminfec2", query = "SELECT r FROM Roles r WHERE r.rolAdminfec2 = :rolAdminfec2")})
public class Roles implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "rol_codigo")
    private Integer rolCodigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "rol_nombre")
    private String rolNombre;
    @Column(name = "rol_activo")
    private Boolean rolActivo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "rol_admining")
    private String rolAdmining;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rol_adminfec1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rolAdminfec1;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "rol_adminact")
    private String rolAdminact;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rol_adminfec2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rolAdminfec2;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rolpRol")
    private Collection<Rolpaginas> rolpaginasCollection;
    @OneToMany(mappedBy = "usuRol")
    private Collection<Usuarios> usuariosCollection;
    
    public Roles() {
    }

    public Roles(Integer rolCodigo) {
        this.rolCodigo = rolCodigo;
    }

    public Roles(Integer rolCodigo, String rolNombre, String rolAdmining, Date rolAdminfec1, String rolAdminact, Date rolAdminfec2) {
        this.rolCodigo = rolCodigo;
        this.rolNombre = rolNombre;
        this.rolAdmining = rolAdmining;
        this.rolAdminfec1 = rolAdminfec1;
        this.rolAdminact = rolAdminact;
        this.rolAdminfec2 = rolAdminfec2;
    }

    public Integer getRolCodigo() {
        return rolCodigo;
    }

    public void setRolCodigo(Integer rolCodigo) {
        this.rolCodigo = rolCodigo;
    }

    public String getRolNombre() {
        return rolNombre;
    }

    public void setRolNombre(String rolNombre) {
        this.rolNombre = rolNombre;
    }

    public Boolean getRolActivo() {
        return rolActivo;
    }

    public void setRolActivo(Boolean rolActivo) {
        this.rolActivo = rolActivo;
    }

    public String getRolAdmining() {
        return rolAdmining;
    }

    public void setRolAdmining(String rolAdmining) {
        this.rolAdmining = rolAdmining;
    }

    public Date getRolAdminfec1() {
        return rolAdminfec1;
    }

    public void setRolAdminfec1(Date rolAdminfec1) {
        this.rolAdminfec1 = rolAdminfec1;
    }

    public String getRolAdminact() {
        return rolAdminact;
    }

    public void setRolAdminact(String rolAdminact) {
        this.rolAdminact = rolAdminact;
    }

    public Date getRolAdminfec2() {
        return rolAdminfec2;
    }

    public void setRolAdminfec2(Date rolAdminfec2) {
        this.rolAdminfec2 = rolAdminfec2;
    }

    @XmlTransient
    public Collection<Rolpaginas> getRolpaginasCollection() {
        return rolpaginasCollection;
    }

    public void setRolpaginasCollection(Collection<Rolpaginas> rolpaginasCollection) {
        this.rolpaginasCollection = rolpaginasCollection;
    }

    @XmlTransient
    public Collection<Usuarios> getUsuariosCollection() {
        return usuariosCollection;
    }

    public void setUsuariosCollection(Collection<Usuarios> usuariosCollection) {
        this.usuariosCollection = usuariosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rolCodigo != null ? rolCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Roles)) {
            return false;
        }
        Roles other = (Roles) object;
        if ((this.rolCodigo == null && other.rolCodigo != null) || (this.rolCodigo != null && !this.rolCodigo.equals(other.rolCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return rolNombre;
    }
    
}
