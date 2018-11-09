package com.cooperativa.coopintranet.controladores;

import com.cooperativa.coopintranet.entidades.Roles;
import com.cooperativa.coopintranet.controladores.util.JsfUtil;
import com.cooperativa.coopintranet.controladores.util.PaginationHelper;
import com.cooperativa.coopintranet.entidades.Usuarios;
import com.cooperativa.coopintranet.servicios.RolesFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

@ManagedBean(name = "rolesController")
@ViewScoped
public class RolesController implements Serializable {

    private Roles current;
    private Usuarios currentUsuarios;
    private List<Roles> items = null;
    private List<Roles> filterItems = null;
    private Roles selectedRoles;
    private String renderedPanel = "false";
    private String renderedGuardar = "false";
    private String renderedEditar = "false";
    @EJB
    private com.cooperativa.coopintranet.servicios.RolesFacade ejbFacade;
    

    public RolesController() {
        items = new ArrayList<Roles>();
        ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
        LoginUsuariosController referenciaBeanSession = (LoginUsuariosController) contexto.getSessionMap().get("loginUsuariosController");
        currentUsuarios = referenciaBeanSession.getUsuarioLogin();
    }
    
    @PostConstruct
    public void inicializar() {
        setCurrent(new Roles());
        setSelectedRoles(new Roles());
    }
    
    private RolesFacade getFacade() {
        return ejbFacade;
    }

    public String prepareList() {
        recreateModel();
        return "mRoles";
    }

    public String prepareCreate() {
        setCurrent(new Roles());
        return "mRoles";
    }

    public String nuevo() {
        setCurrent(new Roles());
        setRenderedPanel("true");
        setRenderedGuardar("true");
        setRenderedEditar("false");
        return null;
    }

    public String create() {
        try {
            getCurrent().setRolAdmining(getCurrentUsuarios().getUsuUsuario());
            getCurrent().setRolAdminfec1(new Date());
            getCurrent().setRolAdminact(getCurrentUsuarios().getUsuUsuario());
            getCurrent().setRolAdminfec2(new Date());
            getFacade().create(getCurrent());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, ResourceBundle.getBundle("/Bundle").getString("MessageInfo"), ResourceBundle.getBundle("/Bundle").getString("RolesCreated")));
            return prepareCreate();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ResourceBundle.getBundle("/Bundle").getString("MessageError"), ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured")));
            return null;
        }
    }

    public String prepareEdit() {
        setCurrent(getSelectedRoles());
        setRenderedGuardar("false");
        setRenderedEditar("true");
        setRenderedPanel("true");
        return null;
    }

    public String prepareView() {
        setCurrent(getSelectedRoles());
        setRenderedGuardar("false");
        setRenderedEditar("false");
        setRenderedPanel("true");
        return null;
    }

    public String update() {
        try {
            getCurrent().setRolAdminact(getCurrentUsuarios().getUsuUsuario());
            getCurrent().setRolAdminfec2(new Date());
            getFacade().edit(getCurrent());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, ResourceBundle.getBundle("/Bundle").getString("MessageInfo"), ResourceBundle.getBundle("/Bundle").getString("RolesUpdated")));
            return "mRoles";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ResourceBundle.getBundle("/Bundle").getString("MessageError"), ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured")));
            return null;
        }
    }

    public String destroy() {
        setCurrent((Roles) getSelectedRoles());
        performDestroy();
        recreateModel();
        return "mRoles";
    }

    public String cancel() {
        setCurrent(null);
        setRenderedPanel("false");
        return null;
    }

    private void performDestroy() {
        try {
            getFacade().remove(getCurrent());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, ResourceBundle.getBundle("/Bundle").getString("MessageInfo"), ResourceBundle.getBundle("/Bundle").getString("RolesDeleted")));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ResourceBundle.getBundle("/Bundle").getString("MessageError"), ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured")));
        }
    }

    public List<Roles> getItems() {
        if (items != null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void recreateModel() {
        items = getFacade().findAll();
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public Roles getCurrent() {
        return current;
    }

    public void setCurrent(Roles current) {
        this.current = current;
    }

    public Usuarios getCurrentUsuarios() {
        return currentUsuarios;
    }

    public void setCurrentUsuarios(Usuarios currentUsuarios) {
        this.currentUsuarios = currentUsuarios;
    }

    public List<Roles> getFilterItems() {
        return filterItems;
    }

    public void setFilterItems(List<Roles> filterItems) {
        this.filterItems = filterItems;
    }

    public Roles getSelectedRoles() {
        return selectedRoles;
    }

    public void setSelectedRoles(Roles selectedRoles) {
        this.selectedRoles = selectedRoles;
    }

    public String getRenderedPanel() {
        return renderedPanel;
    }

    public void setRenderedPanel(String renderedPanel) {
        this.renderedPanel = renderedPanel;
    }

    public String getRenderedGuardar() {
        return renderedGuardar;
    }

    public void setRenderedGuardar(String renderedGuardar) {
        this.renderedGuardar = renderedGuardar;
    }

    public String getRenderedEditar() {
        return renderedEditar;
    }

    public void setRenderedEditar(String renderedEditar) {
        this.renderedEditar = renderedEditar;
    }

    @FacesConverter(forClass = Roles.class)
    public static class RolesControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RolesController controller = (RolesController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "rolesController");
            return controller.ejbFacade.find(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuffer sb = new StringBuffer();
            sb.append(value);
            return sb.toString();
        }

        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Roles) {
                Roles o = (Roles) object;
                return getStringKey(o.getRolCodigo());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Roles.class.getName());
            }
        }
    }
}
