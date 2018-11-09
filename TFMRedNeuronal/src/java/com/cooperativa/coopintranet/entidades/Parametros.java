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
 * @author Diego Ruiz
 */
@Entity
@Table(name = "parametros")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Parametros.findAll", query = "SELECT p FROM Parametros p"),
    @NamedQuery(name = "Parametros.findByParCodigo", query = "SELECT p FROM Parametros p WHERE p.parCodigo = :parCodigo"),
    @NamedQuery(name = "Parametros.findByParNombre", query = "SELECT p.parValor FROM Parametros p WHERE p.parNombre = :parNombre"),
    @NamedQuery(name = "Parametros.findByParValor", query = "SELECT p FROM Parametros p WHERE p.parValor = :parValor"),
    @NamedQuery(name = "Parametros.findByParAdmining", query = "SELECT p FROM Parametros p WHERE p.parAdmining = :parAdmining"),
    @NamedQuery(name = "Parametros.findByParAdminfec1", query = "SELECT p FROM Parametros p WHERE p.parAdminfec1 = :parAdminfec1"),
    @NamedQuery(name = "Parametros.findByParAdminact", query = "SELECT p FROM Parametros p WHERE p.parAdminact = :parAdminact"),
    @NamedQuery(name = "Parametros.findByParAdminfec2", query = "SELECT p FROM Parametros p WHERE p.parAdminfec2 = :parAdminfec2")})
public class Parametros implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "par_codigo")
    private Integer parCodigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "par_nombre")
    private String parNombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "par_valor")
    private String parValor;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "par_admining")
    private String parAdmining;
    @Basic(optional = false)
    @NotNull
    @Column(name = "par_adminfec1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date parAdminfec1;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "par_adminact")
    private String parAdminact;
    @Basic(optional = false)
    @NotNull
    @Column(name = "par_adminfec2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date parAdminfec2;

    public Parametros() {
    }

    public Parametros(Integer parCodigo) {
        this.parCodigo = parCodigo;
    }

    public Parametros(Integer parCodigo, String parNombre, String parValor, String parAdmining, Date parAdminfec1, String parAdminact, Date parAdminfec2) {
        this.parCodigo = parCodigo;
        this.parNombre = parNombre;
        this.parValor = parValor;
        this.parAdmining = parAdmining;
        this.parAdminfec1 = parAdminfec1;
        this.parAdminact = parAdminact;
        this.parAdminfec2 = parAdminfec2;
    }

    public Integer getParCodigo() {
        return parCodigo;
    }

    public void setParCodigo(Integer parCodigo) {
        this.parCodigo = parCodigo;
    }

    public String getParNombre() {
        return parNombre;
    }

    public void setParNombre(String parNombre) {
        this.parNombre = parNombre;
    }

    public String getParValor() {
        return parValor;
    }

    public void setParValor(String parValor) {
        this.parValor = parValor;
    }

    public String getParAdmining() {
        return parAdmining;
    }

    public void setParAdmining(String parAdmining) {
        this.parAdmining = parAdmining;
    }

    public Date getParAdminfec1() {
        return parAdminfec1;
    }

    public void setParAdminfec1(Date parAdminfec1) {
        this.parAdminfec1 = parAdminfec1;
    }

    public String getParAdminact() {
        return parAdminact;
    }

    public void setParAdminact(String parAdminact) {
        this.parAdminact = parAdminact;
    }

    public Date getParAdminfec2() {
        return parAdminfec2;
    }

    public void setParAdminfec2(Date parAdminfec2) {
        this.parAdminfec2 = parAdminfec2;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (parCodigo != null ? parCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Parametros)) {
            return false;
        }
        Parametros other = (Parametros) object;
        if ((this.parCodigo == null && other.parCodigo != null) || (this.parCodigo != null && !this.parCodigo.equals(other.parCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cooperativa.coopintranet.entidades.Parametros[ parCodigo=" + parCodigo + " ]";
    }
    
}
