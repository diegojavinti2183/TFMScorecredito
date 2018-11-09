package com.cooperativa.coopintranet.controladores;

import com.cooperativa.coopintranet.entidades.Paginas;
import com.cooperativa.coopintranet.controladores.util.JsfUtil;
import com.cooperativa.coopintranet.controladores.util.PaginationHelper;
import com.cooperativa.coopintranet.entidades.Usuarios;
import com.cooperativa.coopintranet.servicios.PaginasFacade;

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

@ManagedBean(name = "paginasController")
@ViewScoped
public class PaginasController implements Serializable {

    private Paginas current;
    private Usuarios currentUsuarios;
    private List<Paginas> items = null;
    private List<Paginas> filterItems = null;
    private Paginas selectedPaginas;
    private String renderedPanel = "false";
    private String renderedGuardar = "false";
    private String renderedEditar = "false";
    @EJB
    private com.cooperativa.coopintranet.servicios.PaginasFacade ejbFacade;

    public PaginasController() {
        items = new ArrayList<Paginas>();
        ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
        LoginUsuariosController referenciaBeanSession = (LoginUsuariosController) contexto.getSessionMap().get("loginUsuariosController");
        currentUsuarios = referenciaBeanSession.getUsuarioLogin();
    }

    @PostConstruct
    public void inicializar() {
        setCurrent(new Paginas());
        setSelectedPaginas(new Paginas());
    }

    private PaginasFacade getFacade() {
        return ejbFacade;
    }

    public String prepareList() {
        recreateModel();
        return "mPaginas";
    }

    public String prepareCreate() {
        setCurrent(new Paginas());
        return "mPaginas";
    }

    public String nuevo() {
        setCurrent(new Paginas());
        setRenderedPanel("true");
        setRenderedGuardar("true");
        setRenderedEditar("false");
        return null;
    }

    public String create() {
        try {
            getCurrent().setPagAdmining(getCurrentUsuarios().getUsuUsuario());
            getCurrent().setPagAdminfec1(new Date());
            getCurrent().setPagAdminact(getCurrentUsuarios().getUsuUsuario());
            getCurrent().setPagAdminfec2(new Date());
            getFacade().create(getCurrent());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, ResourceBundle.getBundle("/Bundle").getString("MessageInfo"), ResourceBundle.getBundle("/Bundle").getString("PaginasCreated")));
            return prepareCreate();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ResourceBundle.getBundle("/Bundle").getString("MessageError"), ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured")));
            return null;
        }
    }

    public String prepareEdit() {
        setCurrent(getSelectedPaginas());
        setRenderedGuardar("false");
        setRenderedEditar("true");
        setRenderedPanel("true");
        return null;
    }

    public String prepareView() {
        setCurrent(getSelectedPaginas());
        setRenderedGuardar("false");
        setRenderedEditar("false");
        setRenderedPanel("true");
        return null;
    }

    public String update() {
        try {
            getCurrent().setPagAdminact(getCurrentUsuarios().getUsuUsuario());
            getCurrent().setPagAdminfec2(new Date());
            getFacade().edit(getCurrent());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, ResourceBundle.getBundle("/Bundle").getString("MessageInfo"), ResourceBundle.getBundle("/Bundle").getString("PaginasUpdated")));
            return "mPaginas";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ResourceBundle.getBundle("/Bundle").getString("MessageError"), ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured")));
            return null;
        }
    }

    public String destroy() {
        setCurrent((Paginas) getSelectedPaginas());
        performDestroy();
        recreateModel();
        return "mPaginas";
    }

    public String cancel() {
        setCurrent(null);
        setRenderedPanel("false");
        return null;
    }

    private void performDestroy() {
        try {
            getFacade().remove(getCurrent());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, ResourceBundle.getBundle("/Bundle").getString("MessageInfo"), ResourceBundle.getBundle("/Bundle").getString("PaginasDeleted")));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ResourceBundle.getBundle("/Bundle").getString("MessageError"), ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured")));
        }
    }

    public List<Paginas> getItems() {
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

    public Paginas getCurrent() {
        return current;
    }

    public void setCurrent(Paginas current) {
        this.current = current;
    }

    public Usuarios getCurrentUsuarios() {
        return currentUsuarios;
    }

    public void setCurrentUsuarios(Usuarios currentUsuarios) {
        this.currentUsuarios = currentUsuarios;
    }

    public List<Paginas> getFilterItems() {
        return filterItems;
    }

    public void setFilterItems(List<Paginas> filterItems) {
        this.filterItems = filterItems;
    }

    public Paginas getSelectedPaginas() {
        return selectedPaginas;
    }

    public void setSelectedPaginas(Paginas selectedPaginas) {
        this.selectedPaginas = selectedPaginas;
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

    @FacesConverter(forClass = Paginas.class)
    public static class PaginasControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PaginasController controller = (PaginasController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "paginasController");
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
            if (object instanceof Paginas) {
                Paginas o = (Paginas) object;
                return getStringKey(o.getPagCodigo());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Paginas.class.getName());
            }
        }
    }
}
