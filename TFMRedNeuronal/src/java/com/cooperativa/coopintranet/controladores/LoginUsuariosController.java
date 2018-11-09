package com.cooperativa.coopintranet.controladores;

import com.cooperativa.coopintranet.controladores.util.JsfUtil;
import com.cooperativa.coopintranet.entidades.Usuarios;
import com.cooperativa.coopintranet.servicios.LoginUsuariosFacade;

import java.io.Serializable;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

@ManagedBean(name = "loginUsuariosController")
@SessionScoped
public class LoginUsuariosController implements Serializable {

    private Usuarios current;
    private Usuarios usuarioLogin;
    private String usuario;
    private String contrasena;
    private SelectItem[] SelectCajas;
    @EJB
    private com.cooperativa.coopintranet.servicios.LoginUsuariosFacade ejbFacade;
    
    public LoginUsuariosController() {
    }

    @PostConstruct
    public void inicializar() {
        current = new Usuarios();
        setUsuarioLogin(null);
    }

    private LoginUsuariosFacade getFacade() {
        return ejbFacade;
    }
    
    public String prepareMarcacion() {
        return "/registros/Marcacion";
    }

    public String userLogin() {
        String result = null;
        usuarioLogin = new Usuarios();
        usuarioLogin = getFacade().UserLogin(usuario, contrasena);
        if (usuarioLogin.getUsuUsuario() != null && usuarioLogin.getUsuUsuario().equals(usuario)) {
            result = "menus";
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ResourceBundle.getBundle("/Bundle").getString("MessageError"), ResourceBundle.getBundle("/Bundle").getString("LoginError")));
        }
        return result;
    }

    
    public Usuarios getUsuarioLogin() {
        return usuarioLogin;
    }

    public void setUsuarioLogin(Usuarios usuarioLogin) {
        this.usuarioLogin = usuarioLogin;
    }

    public Usuarios getCurrent() {
        return current;
    }

    public void setCurrent(Usuarios current) {
        this.current = current;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public SelectItem[] getSelectCajas() {
        return SelectCajas;
    }

    public void setSelectCajas(SelectItem[] SelectCajas) {
        this.SelectCajas = SelectCajas;
    }

   
}
