package com.cooperativa.coopintranet.controladores;

import com.cooperativa.coopintranet.entidades.Personas;
import com.cooperativa.coopintranet.controladores.util.JsfUtil;
import com.cooperativa.coopintranet.controladores.util.PaginationHelper;
import com.cooperativa.coopintranet.entidades.Usuarios;
import com.cooperativa.coopintranet.servicios.PersonasFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

@ManagedBean(name = "personasController")
@ViewScoped
public class PersonasController implements Serializable {

    private Personas current;
    private List<Personas> items = null;
    private List<Personas> filterItems = null;
    private Personas selectedPersonas;
    private Usuarios currentUsuarios;
    private String renderedPanel = "false";
    private String renderedGuardar = "false";
    private String renderedEditar = "false";
    @EJB
    private com.cooperativa.coopintranet.servicios.PersonasFacade ejbFacade;

    public PersonasController() {
        items = new ArrayList<Personas>();
        ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();

        LoginUsuariosController referenciaBeanSession = (LoginUsuariosController) contexto.getSessionMap().get("loginUsuariosController");
        currentUsuarios = referenciaBeanSession.getUsuarioLogin();
    }

    @PostConstruct
    public void inicializar() {
        setCurrent(new Personas());
        setSelectedPersonas(new Personas());
    }

    private PersonasFacade getFacade() {
        return ejbFacade;
    }

    public String prepareList() {
        //recreateModel();
        return "List";
    }

    public String nuevo() {
        setCurrent(new Personas());
        setRenderedPanel("true");
        setRenderedGuardar("true");
        setRenderedEditar("false");
        return null;
    }

    public String prepareView() {
        setCurrent(getSelectedPersonas());
        setRenderedGuardar("false");
        setRenderedEditar("false");
        setRenderedPanel("true");
        return null;
    }

    public String prepareCreate() {
        setCurrent(new Personas());
        return "Create";
    }

    public String create() {
        try {
            getCurrent().setPerAdmining(getCurrentUsuarios().getUsuUsuario());
            getCurrent().setPerAdminfec1(new Date());
            getCurrent().setPerAdminact(getCurrentUsuarios().getUsuUsuario());
            getCurrent().setPerAdminfec2(new Date());
            getFacade().create(getCurrent());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PersonasCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        setCurrent(getSelectedPersonas());
        setRenderedGuardar("false");
        setRenderedEditar("true");
        setRenderedPanel("true");
        return null;
    }

    public String update() {
        try {
            getCurrent().setPerAdminact(getCurrentUsuarios().getUsuUsuario());
            getCurrent().setPerAdminfec2(new Date());
            getFacade().edit(getCurrent());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PersonasUpdated"));
            return "Create";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String cancel() {
        setCurrent(new Personas());
        setSelectedPersonas(new Personas());
        setRenderedPanel("false");
        return "Create";
    }

    public String destroy() {
        setCurrent((Personas) getSelectedPersonas());
        performDestroy();

        return "Create";
    }

    private void performDestroy() {
        try {
            getFacade().remove(getCurrent());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PersonasDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    public List<Personas> getItems() {
        items = getFacade().findAll();
        return items;
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public Personas getPersonas(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    public Personas getCurrent() {
        return current;
    }

    public void setCurrent(Personas current) {
        this.current = current;
    }

    public List<Personas> getFilterItems() {
        return filterItems;
    }

    public void setFilterItems(List<Personas> filterItems) {
        this.filterItems = filterItems;
    }

    public Personas getSelectedPersonas() {
        return selectedPersonas;
    }

    public void setSelectedPersonas(Personas selectedPersonas) {
        this.selectedPersonas = selectedPersonas;
    }

    public Usuarios getCurrentUsuarios() {
        return currentUsuarios;
    }

    public void setCurrentUsuarios(Usuarios currentUsuarios) {
        this.currentUsuarios = currentUsuarios;
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

    @FacesConverter(forClass = Personas.class)
    public static class PersonasControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PersonasController controller = (PersonasController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "personasController");
            return controller.getPersonas(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Personas) {
                Personas o = (Personas) object;
                return getStringKey(o.getPerCodigo());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Personas.class.getName());
            }
        }

    }

}
