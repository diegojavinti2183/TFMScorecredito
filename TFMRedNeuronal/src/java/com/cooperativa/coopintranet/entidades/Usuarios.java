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
 * @author Diego Ruiz
 */
@Entity
@Table(name = "usuarios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuarios.findAll", query = "SELECT u FROM Usuarios u"),
    @NamedQuery(name = "Usuarios.findByUsuarioLogin", query = "SELECT u FROM Usuarios u WHERE u.usuUsuario = :usuUsuario and u.usuPassword = :usuPassword and u.usuActivo = true"),
    @NamedQuery(name = "Usuarios.findByUsuCodigo", query = "SELECT u FROM Usuarios u WHERE u.usuCodigo = :usuCodigo"),
    @NamedQuery(name = "Usuarios.findByUsuRoles", query = "SELECT count(u.usuCodigo) FROM Usuarios u WHERE u.usuRol.rolCodigo = :rolCodigo AND u.usuActivo = true"),
    @NamedQuery(name = "Usuarios.findByUsuariosRol", query = "SELECT u FROM Usuarios u WHERE u.usuRol.rolCodigo = :rolCodigo AND u.usuActivo = true"),
    @NamedQuery(name = "Usuarios.findByUsuariosRolLista", query = "SELECT u FROM Usuarios u WHERE u.usuRol.rolCodigo IN :lista AND u.usuActivo = true"),
    @NamedQuery(name = "Usuarios.findByUsuUsuario", query = "SELECT u FROM Usuarios u WHERE u.usuUsuario = :usuUsuario"),
    @NamedQuery(name = "Usuarios.findByUsuPassword", query = "SELECT u FROM Usuarios u WHERE u.usuPassword = :usuPassword"),
    @NamedQuery(name = "Usuarios.findByUsuActivo", query = "SELECT u FROM Usuarios u WHERE u.usuActivo = :usuActivo"),
    @NamedQuery(name = "Usuarios.findByUsuNombre", query = "SELECT u FROM Usuarios u WHERE u.usuNombre = :usuNombre"),
    @NamedQuery(name = "Usuarios.findByUsuAdmining", query = "SELECT u FROM Usuarios u WHERE u.usuAdmining = :usuAdmining"),
    @NamedQuery(name = "Usuarios.findByUsuAdminfec1", query = "SELECT u FROM Usuarios u WHERE u.usuAdminfec1 = :usuAdminfec1"),
    @NamedQuery(name = "Usuarios.findByUsuAdminact", query = "SELECT u FROM Usuarios u WHERE u.usuAdminact = :usuAdminact"),
    @NamedQuery(name = "Usuarios.findByUsuAdminfec2", query = "SELECT u FROM Usuarios u WHERE u.usuAdminfec2 = :usuAdminfec2")})
public class Usuarios implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "usu_codigo")
    private Integer usuCodigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "usu_usuario")
    private String usuUsuario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "usu_password")
    private String usuPassword;
    @Column(name = "usu_activo")
    private Boolean usuActivo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "usu_nombre")
    private String usuNombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "usu_admining")
    private String usuAdmining;
    @Basic(optional = false)
    @NotNull
    @Column(name = "usu_adminfec1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date usuAdminfec1;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "usu_adminact")
    private String usuAdminact;
    @Basic(optional = false)
    @NotNull
    @Column(name = "usu_adminfec2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date usuAdminfec2;
    @JoinColumn(name = "usu_rol", referencedColumnName = "rol_codigo")
    @ManyToOne
    private Roles usuRol;
   

    public Usuarios() {
    }

    public Usuarios(Integer usuCodigo) {
        this.usuCodigo = usuCodigo;
    }

    public Usuarios(Integer usuCodigo, String usuUsuario, String usuPassword, String usuNombre, String usuAdmining, Date usuAdminfec1, String usuAdminact, Date usuAdminfec2) {
        this.usuCodigo = usuCodigo;
        this.usuUsuario = usuUsuario;
        this.usuPassword = usuPassword;
        this.usuNombre = usuNombre;
        this.usuAdmining = usuAdmining;
        this.usuAdminfec1 = usuAdminfec1;
        this.usuAdminact = usuAdminact;
        this.usuAdminfec2 = usuAdminfec2;
    }

    public Integer getUsuCodigo() {
        return usuCodigo;
    }

    public void setUsuCodigo(Integer usuCodigo) {
        this.usuCodigo = usuCodigo;
    }

    public String getUsuUsuario() {
        return usuUsuario;
    }

    public void setUsuUsuario(String usuUsuario) {
        this.usuUsuario = usuUsuario;
    }

    public String getUsuPassword() {
        return usuPassword;
    }

    public void setUsuPassword(String usuPassword) {
        this.usuPassword = usuPassword;
    }

    public Boolean getUsuActivo() {
        return usuActivo;
    }

    public void setUsuActivo(Boolean usuActivo) {
        this.usuActivo = usuActivo;
    }

    public String getUsuNombre() {
        return usuNombre;
    }

    public void setUsuNombre(String usuNombre) {
        this.usuNombre = usuNombre;
    }

    public String getUsuAdmining() {
        return usuAdmining;
    }

    public void setUsuAdmining(String usuAdmining) {
        this.usuAdmining = usuAdmining;
    }

    public Date getUsuAdminfec1() {
        return usuAdminfec1;
    }

    public void setUsuAdminfec1(Date usuAdminfec1) {
        this.usuAdminfec1 = usuAdminfec1;
    }

    public String getUsuAdminact() {
        return usuAdminact;
    }

    public void setUsuAdminact(String usuAdminact) {
        this.usuAdminact = usuAdminact;
    }

    public Date getUsuAdminfec2() {
        return usuAdminfec2;
    }

    public void setUsuAdminfec2(Date usuAdminfec2) {
        this.usuAdminfec2 = usuAdminfec2;
    }

    public Roles getUsuRol() {
        return usuRol;
    }

    public void setUsuRol(Roles usuRol) {
        this.usuRol = usuRol;
    }
    
  

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuCodigo != null ? usuCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuarios)) {
            return false;
        }
        Usuarios other = (Usuarios) object;
        if ((this.usuCodigo == null && other.usuCodigo != null) || (this.usuCodigo != null && !this.usuCodigo.equals(other.usuCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cooperativa.coopintranet.entidades.Usuarios[ usuCodigo=" + usuCodigo + " ]";
    }
    
}
