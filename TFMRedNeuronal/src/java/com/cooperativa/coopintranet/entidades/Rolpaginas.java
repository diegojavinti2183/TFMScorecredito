/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cooperativa.coopintranet.entidades;

import java.io.Serializable;
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
 * @author Diego Ruiz
 */
@Entity
@Table(name = "rolpaginas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rolpaginas.findAll", query = "SELECT r FROM Rolpaginas r"),    
    @NamedQuery(name = "Rolpaginas.findByRolpRoles", query = "SELECT r FROM Rolpaginas r WHERE r.rolpRol = :rolpRol"),
    @NamedQuery(name = "Rolpaginas.findByRolpCodigo", query = "SELECT r FROM Rolpaginas r WHERE r.rolpCodigo = :rolpCodigo"),
    @NamedQuery(name = "Rolpaginas.findByRolpAdmining", query = "SELECT r FROM Rolpaginas r WHERE r.rolpAdmining = :rolpAdmining"),
    @NamedQuery(name = "Rolpaginas.findByRolpAdminfec1", query = "SELECT r FROM Rolpaginas r WHERE r.rolpAdminfec1 = :rolpAdminfec1"),
    @NamedQuery(name = "Rolpaginas.findByRolpAdminact", query = "SELECT r FROM Rolpaginas r WHERE r.rolpAdminact = :rolpAdminact"),
    @NamedQuery(name = "Rolpaginas.findByRolpAdminfec2", query = "SELECT r FROM Rolpaginas r WHERE r.rolpAdminfec2 = :rolpAdminfec2")})
public class Rolpaginas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "rolp_codigo")
    private Integer rolpCodigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "rolp_admining")
    private String rolpAdmining;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rolp_adminfec1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rolpAdminfec1;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "rolp_adminact")
    private String rolpAdminact;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rolp_adminfec2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rolpAdminfec2;
    @JoinColumn(name = "rolp_rol", referencedColumnName = "rol_codigo")
    @ManyToOne(optional = false)
    private Roles rolpRol;
    @JoinColumn(name = "rolp_pagina", referencedColumnName = "pag_codigo")
    @ManyToOne(optional = false)
    private Paginas rolpPagina;

    public Rolpaginas() {
    }

    public Rolpaginas(Integer rolpCodigo) {
        this.rolpCodigo = rolpCodigo;
    }

    public Rolpaginas(Integer rolpCodigo, String rolpAdmining, Date rolpAdminfec1, String rolpAdminact, Date rolpAdminfec2) {
        this.rolpCodigo = rolpCodigo;
        this.rolpAdmining = rolpAdmining;
        this.rolpAdminfec1 = rolpAdminfec1;
        this.rolpAdminact = rolpAdminact;
        this.rolpAdminfec2 = rolpAdminfec2;
    }

    public Integer getRolpCodigo() {
        return rolpCodigo;
    }

    public void setRolpCodigo(Integer rolpCodigo) {
        this.rolpCodigo = rolpCodigo;
    }

    public String getRolpAdmining() {
        return rolpAdmining;
    }

    public void setRolpAdmining(String rolpAdmining) {
        this.rolpAdmining = rolpAdmining;
    }

    public Date getRolpAdminfec1() {
        return rolpAdminfec1;
    }

    public void setRolpAdminfec1(Date rolpAdminfec1) {
        this.rolpAdminfec1 = rolpAdminfec1;
    }

    public String getRolpAdminact() {
        return rolpAdminact;
    }

    public void setRolpAdminact(String rolpAdminact) {
        this.rolpAdminact = rolpAdminact;
    }

    public Date getRolpAdminfec2() {
        return rolpAdminfec2;
    }

    public void setRolpAdminfec2(Date rolpAdminfec2) {
        this.rolpAdminfec2 = rolpAdminfec2;
    }

    public Roles getRolpRol() {
        return rolpRol;
    }

    public void setRolpRol(Roles rolpRol) {
        this.rolpRol = rolpRol;
    }

    public Paginas getRolpPagina() {
        return rolpPagina;
    }

    public void setRolpPagina(Paginas rolpPagina) {
        this.rolpPagina = rolpPagina;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rolpCodigo != null ? rolpCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rolpaginas)) {
            return false;
        }
        Rolpaginas other = (Rolpaginas) object;
        if ((this.rolpCodigo == null && other.rolpCodigo != null) || (this.rolpCodigo != null && !this.rolpCodigo.equals(other.rolpCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cooperativa.coopintranet.entidades.Rolpaginas[ rolpCodigo=" + rolpCodigo + " ]";
    }
    
}
