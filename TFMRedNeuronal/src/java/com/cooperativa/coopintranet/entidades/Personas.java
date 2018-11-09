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
 * @author DiegoJavier
 */
@Entity
@Table(name = "personas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Personas.findAll", query = "SELECT p FROM Personas p"),
    @NamedQuery(name = "Personas.findByPerCodigo", query = "SELECT p FROM Personas p WHERE p.perCodigo = :perCodigo"),
    @NamedQuery(name = "Personas.findByPerIdentificacion", query = "SELECT p FROM Personas p WHERE p.perIdentificacion = :identificacion"),
    @NamedQuery(name = "Personas.findByPerApellidopaterno", query = "SELECT p FROM Personas p WHERE p.perApellidopaterno = :perApellidopaterno"),
    @NamedQuery(name = "Personas.findByPerApellidomaterno", query = "SELECT p FROM Personas p WHERE p.perApellidomaterno = :perApellidomaterno"),
    @NamedQuery(name = "Personas.findByPerPrimernombre", query = "SELECT p FROM Personas p WHERE p.perPrimernombre = :perPrimernombre"),
    @NamedQuery(name = "Personas.findByPerSegundonombre", query = "SELECT p FROM Personas p WHERE p.perSegundonombre = :perSegundonombre"),
    @NamedQuery(name = "Personas.findByPerFechanacimiento", query = "SELECT p FROM Personas p WHERE p.perFechanacimiento = :perFechanacimiento"),
    @NamedQuery(name = "Personas.findByPerCodigoSocio", query = "SELECT p FROM Personas p WHERE p.perSocio = :codigo"),
    @NamedQuery(name = "Personas.findByPerGenero", query = "SELECT p FROM Personas p WHERE p.perGenero = :perGenero"),
    @NamedQuery(name = "Personas.findByPerAgencia", query = "SELECT p FROM Personas p WHERE p.perAgencia = :perAgencia"),
    @NamedQuery(name = "Personas.findByPerDireccion", query = "SELECT p FROM Personas p WHERE p.perDireccion = :perDireccion"),
    @NamedQuery(name = "Personas.findByPerTelefono", query = "SELECT p FROM Personas p WHERE p.perTelefono = :perTelefono"),
    @NamedQuery(name = "Personas.findByPerEstado", query = "SELECT p FROM Personas p WHERE p.perEstado = :perEstado"),
    @NamedQuery(name = "Personas.findByPerAdmining", query = "SELECT p FROM Personas p WHERE p.perAdmining = :perAdmining"),
    @NamedQuery(name = "Personas.findByPerAdminfec1", query = "SELECT p FROM Personas p WHERE p.perAdminfec1 = :perAdminfec1"),
    @NamedQuery(name = "Personas.findByPerAdminact", query = "SELECT p FROM Personas p WHERE p.perAdminact = :perAdminact"),
    @NamedQuery(name = "Personas.findByPerAdminfec2", query = "SELECT p FROM Personas p WHERE p.perAdminfec2 = :perAdminfec2")})
public class Personas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "per_codigo")
    private Integer perCodigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "per_identificacion",unique=true)
    private String perIdentificacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "per_apellidopaterno")
    private String perApellidopaterno;
    @Basic(optional = false)   
    @Size(min = 1, max = 200)
    @Column(name = "per_apellidomaterno")
    private String perApellidomaterno;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "per_primernombre")
    private String perPrimernombre;
    @Basic(optional = false)
    @Size(min = 1, max = 200)
    @Column(name = "per_segundonombre")
    private String perSegundonombre;
    @Column(name = "per_fechanacimiento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date perFechanacimiento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "per_genero")
    private Character perGenero;
    @Column(name = "per_agencia")
    private Integer perAgencia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "per_direccion")
    private String perDireccion;
    @Basic(optional = false)
    @Size(min = 1, max = 20)
    @Column(name = "per_telefono")
    private String perTelefono;
    @Basic(optional = false)
    @NotNull
    @Column(name = "per_tipo")
    private Character perTipo;
    @Column(name = "per_socio")
    private Integer perSocio;
    @NotNull
    @Column(name = "per_estado")
    private Boolean perEstado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "per_admining")
    private String perAdmining;
    @Basic(optional = false)
    @NotNull
    @Column(name = "per_adminfec1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date perAdminfec1;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "per_adminact")
    private String perAdminact;
    @Basic(optional = false)
    @NotNull
    @Column(name = "per_adminfec2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date perAdminfec2;
    

    public Personas() {
    }

    public Personas(Integer perCodigo) {
        this.perCodigo = perCodigo;
    }

    public Personas(Integer perCodigo, String perIdentificacion, String perApellidopaterno, String perApellidomaterno, String perPrimernombre, String perSegundonombre,Character perGenero, String perDireccion, String perTelefono,Character perTipo,Boolean perEstado, String perAdmining, Date perAdminfec1, String perAdminact, Date perAdminfec2) {
        this.perCodigo = perCodigo;
        this.perIdentificacion = perIdentificacion;
        this.perApellidopaterno = perApellidopaterno;
        this.perApellidomaterno = perApellidomaterno;
        this.perPrimernombre = perPrimernombre;
        this.perSegundonombre = perSegundonombre;
        this.perGenero = perGenero;
        this.perDireccion = perDireccion;
        this.perTelefono = perTelefono;
        this.perTipo = perTipo;
        this.perEstado = perEstado;
        this.perAdmining = perAdmining;
        this.perAdminfec1 = perAdminfec1;
        this.perAdminact = perAdminact;
        this.perAdminfec2 = perAdminfec2;
    }

    public Integer getPerCodigo() {
        return perCodigo;
    }

    public void setPerCodigo(Integer perCodigo) {
        this.perCodigo = perCodigo;
    }

    public String getPerIdentificacion() {
        return perIdentificacion;
    }

    public void setPerIdentificacion(String perIdentificacion) {
        this.perIdentificacion = perIdentificacion;
    }

    public String getPerApellidopaterno() {
        return perApellidopaterno;
    }

    public void setPerApellidopaterno(String perApellidopaterno) {
        this.perApellidopaterno = perApellidopaterno;
    }

    public String getPerApellidomaterno() {
        return perApellidomaterno;
    }

    public void setPerApellidomaterno(String perApellidomaterno) {
        this.perApellidomaterno = perApellidomaterno;
    }

    public String getPerPrimernombre() {
        return perPrimernombre;
    }

    public void setPerPrimernombre(String perPrimernombre) {
        this.perPrimernombre = perPrimernombre;
    }

    public String getPerSegundonombre() {
        return perSegundonombre;
    }

    public void setPerSegundonombre(String perSegundonombre) {
        this.perSegundonombre = perSegundonombre;
    }

    public Date getPerFechanacimiento() {
        return perFechanacimiento;
    }

    public void setPerFechanacimiento(Date perFechanacimiento) {
        this.perFechanacimiento = perFechanacimiento;
    }

    public Character getPerGenero() {
        return perGenero;
    }

    public void setPerGenero(Character perGenero) {
        this.perGenero = perGenero;
    }

    public Integer getPerAgencia() {
        return perAgencia;
    }

    public void setPerAgencia(Integer perAgencia) {
        this.perAgencia = perAgencia;
    }

    public String getPerDireccion() {
        return perDireccion;
    }

    public void setPerDireccion(String perDireccion) {
        this.perDireccion = perDireccion;
    }

    public String getPerTelefono() {
        return perTelefono;
    }

    public void setPerTelefono(String perTelefono) {
        this.perTelefono = perTelefono;
    }

    public Boolean getPerEstado() {
        return perEstado;
    }

    public void setPerEstado(Boolean perEstado) {
        this.perEstado = perEstado;
    }

    public String getPerAdmining() {
        return perAdmining;
    }

    public void setPerAdmining(String perAdmining) {
        this.perAdmining = perAdmining;
    }

    public Date getPerAdminfec1() {
        return perAdminfec1;
    }

    public void setPerAdminfec1(Date perAdminfec1) {
        this.perAdminfec1 = perAdminfec1;
    }

    public String getPerAdminact() {
        return perAdminact;
    }

    public void setPerAdminact(String perAdminact) {
        this.perAdminact = perAdminact;
    }

    public Date getPerAdminfec2() {
        return perAdminfec2;
    }

    public void setPerAdminfec2(Date perAdminfec2) {
        this.perAdminfec2 = perAdminfec2;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (perCodigo != null ? perCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Personas)) {
            return false;
        }
        Personas other = (Personas) object;
        if ((this.perCodigo == null && other.perCodigo != null) || (this.perCodigo != null && !this.perCodigo.equals(other.perCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return perIdentificacion+" "+perApellidopaterno+" "+perApellidomaterno+" "+perPrimernombre+" "+perSegundonombre;        
    }

    public Character getPerTipo() {
        return perTipo;
    }

    public void setPerTipo(Character perTipo) {
        this.perTipo = perTipo;
    }

    /**
     * @return the perSocio
     */
    public Integer getPerSocio() {
        return perSocio;
    }

    /**
     * @param perSocio the perSocio to set
     */
    public void setPerSocio(Integer perSocio) {
        this.perSocio = perSocio;
    }
    
}
