package com.cooperativa.coopintranet.controladores;

import com.cooperativa.coopintranet.entidades.Usuarios;
import com.cooperativa.coopintranet.controladores.util.JsfUtil;
import com.cooperativa.coopintranet.controladores.util.PaginationHelper;
import com.cooperativa.coopintranet.servicios.UsuariosFacade;

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

@ManagedBean(name = "usuariosController")
@ViewScoped
public class UsuariosController implements Serializable {

    private Usuarios current;
    private Usuarios currentUsuarios;
    private List<Usuarios> items = null;
    private List<Usuarios> filterItems = null;
    private Usuarios selectedUsuarios;
    private String renderedPanel = "false";
    private String renderedGuardar = "false";
    private String renderedEditar = "false";
    private String anterior;
    private String nueva;
    private String confirma;

    @EJB
    private com.cooperativa.coopintranet.servicios.UsuariosFacade ejbFacade;

    public UsuariosController() {
        items = new ArrayList<Usuarios>();
        ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
        LoginUsuariosController referenciaBeanSession = (LoginUsuariosController) contexto.getSessionMap().get("loginUsuariosController");
        currentUsuarios = referenciaBeanSession.getUsuarioLogin();
    }

    @PostConstruct
    public void inicializar() {
        setCurrent(new Usuarios());
        setSelectedUsuarios(new Usuarios());
    }

    private UsuariosFacade getFacade() {
        return ejbFacade;
    }

    public String prepareList() {
        recreateModel();
        return "Create";
    }

    public String prepareCreate() {
        setCurrent(new Usuarios());
        return "Create";
    }

    public String prepareCreate2() {
        setCurrent(new Usuarios());
        return "/menus";
    }

    public String nuevo() {
        setCurrent(new Usuarios());
        setRenderedPanel("true");
        setRenderedGuardar("true");
        setRenderedEditar("false");
        return null;
    }

    public String create() {
        try {
            getCurrent().setUsuAdmining(getCurrentUsuarios().getUsuUsuario());
            getCurrent().setUsuAdminfec1(new Date());
            getCurrent().setUsuAdminact(getCurrentUsuarios().getUsuUsuario());
            getCurrent().setUsuAdminfec2(new Date());
            getFacade().create(getCurrent());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, ResourceBundle.getBundle("/Bundle").getString("MessageInfo"), ResourceBundle.getBundle("/Bundle").getString("UsuariosCreated")));
            return prepareCreate();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ResourceBundle.getBundle("/Bundle").getString("MessageError"), ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured")));
            return null;
        }
    }

    public String changePassword() {
        try {
            currentUsuarios.setUsuPassword(current.getUsuPassword());
            current = currentUsuarios;
            getFacade().edit(current);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, ResourceBundle.getBundle("/Bundle").getString("MessageInfo"), ResourceBundle.getBundle("/Bundle").getString("UsuariosEditPassword")));
            return prepareCreate2();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ResourceBundle.getBundle("/Bundle").getString("MessageError"), ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured")));
            return null;
        }
    }

    public String prepareEdit() {
        setCurrent(getSelectedUsuarios());
        setRenderedGuardar("false");
        setRenderedEditar("true");
        setRenderedPanel("true");
        return null;
    }

    public String prepareView() {
        setCurrent(getSelectedUsuarios());
        setRenderedGuardar("false");
        setRenderedEditar("false");
        setRenderedPanel("true");
        return null;
    }

    public String update() {
        try {
            getCurrent().setUsuAdminact(getCurrentUsuarios().getUsuUsuario());
            getCurrent().setUsuAdminfec2(new Date());
            getFacade().edit(getCurrent());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, ResourceBundle.getBundle("/Bundle").getString("MessageInfo"), ResourceBundle.getBundle("/Bundle").getString("UsuariosUpdated")));
            return "Create";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ResourceBundle.getBundle("/Bundle").getString("MessageError"), ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured")));
            return null;
        }
    }

    public String cambio() {
        try {
            if (nueva.equals(confirma)) {
                if (anterior.equals(currentUsuarios.getUsuPassword())) {
                    current = currentUsuarios;
                    current.setUsuPassword(nueva);
                    getFacade().edit(getCurrent());
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, ResourceBundle.getBundle("/Bundle").getString("MessageInfo"), ResourceBundle.getBundle("/Bundle").getString("ClaveUpdated")));
                    return "/menus";
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, ResourceBundle.getBundle("/Bundle").getString("MessageInfo"), ResourceBundle.getBundle("/Bundle").getString("ClaveIncorrecta")));
                    return null;
                }

            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, ResourceBundle.getBundle("/Bundle").getString("MessageInfo"), ResourceBundle.getBundle("/Bundle").getString("ClaveDistinta")));
                return null;

            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ResourceBundle.getBundle("/Bundle").getString("MessageError"), ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured")));
            return null;
        }
    }

    public String destroy() {
        setCurrent((Usuarios) getSelectedUsuarios());
        performDestroy();
        recreateModel();
        return "Create";
    }

    public String cancel() {
        setCurrent(null);
        setRenderedPanel("false");
        return null;
    }

    private void performDestroy() {
        try {
            getFacade().remove(getCurrent());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, ResourceBundle.getBundle("/Bundle").getString("MessageInfo"), ResourceBundle.getBundle("/Bundle").getString("UsuariosDeleted")));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ResourceBundle.getBundle("/Bundle").getString("MessageError"), ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured")));
        }
    }

    public List<Usuarios> getItems() {
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

    public Usuarios getCurrent() {
        return current;
    }

    public void setCurrent(Usuarios current) {
        this.current = current;
    }

    public Usuarios getCurrentUsuarios() {
        return currentUsuarios;
    }

    public void setCurrentUsuarios(Usuarios currentUsuarios) {
        this.currentUsuarios = currentUsuarios;
    }

    public List<Usuarios> getFilterItems() {
        return filterItems;
    }

    public void setFilterItems(List<Usuarios> filterItems) {
        this.filterItems = filterItems;
    }

    public Usuarios getSelectedUsuarios() {
        return selectedUsuarios;
    }

    public void setSelectedUsuarios(Usuarios selectedUsuarios) {
        this.selectedUsuarios = selectedUsuarios;
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

    public String getAnterior() {
        return anterior;
    }

    public void setAnterior(String anterior) {
        this.anterior = anterior;
    }

    public String getNueva() {
        return nueva;
    }

    public void setNueva(String nueva) {
        this.nueva = nueva;
    }

    public String getConfirma() {
        return confirma;
    }

    public void setConfirma(String confirma) {
        this.confirma = confirma;
    }

    @FacesConverter(forClass = Usuarios.class)
    public static class UsuariosControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            UsuariosController controller = (UsuariosController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "usuariosController");
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
            if (object instanceof Usuarios) {
                Usuarios o = (Usuarios) object;
                return getStringKey(o.getUsuCodigo());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Usuarios.class.getName());
            }
        }
    }
}
