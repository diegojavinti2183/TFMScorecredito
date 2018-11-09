package com.cooperativa.coopintranet.controladores;

import com.cooperativa.coopintranet.entidades.Categoriasred;
import com.cooperativa.coopintranet.controladores.util.JsfUtil;
import com.cooperativa.coopintranet.controladores.util.PaginationHelper;
import com.cooperativa.coopintranet.entidades.Usuarios;
import com.cooperativa.coopintranet.servicios.CategoriasredFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
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

@ManagedBean(name ="categoriasredController")
@ViewScoped
public class CategoriasredController implements Serializable {

    private Categoriasred current;
    private Categoriasred currentCategoriasred;
    private Usuarios currentUsuarios; 
    private List<Categoriasred> items = null;
    private List<Categoriasred> filterItems = null;
    private Categoriasred selectedCategoriasred;
    private String renderedPanel = "false";
    private String renderedGuardar = "false";
    private String renderedEditar = "false";
    @EJB
    private com.cooperativa.coopintranet.servicios.CategoriasredFacade ejbFacade;
    

    public CategoriasredController() {
        items = new ArrayList<Categoriasred>();
        ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
        LoginUsuariosController referenciaBeanSession = (LoginUsuariosController) contexto.getSessionMap().get("loginUsuariosController");
        currentUsuarios = referenciaBeanSession.getUsuarioLogin();
    }

    private CategoriasredFacade getFacade() {
        return ejbFacade;
    }

     public String prepareList() {
        recreateModel();
        return "List";
    }
    
    public String nuevo() {
        setCurrent(new Categoriasred());
        setRenderedPanel("true");
        setRenderedGuardar("true");
        setRenderedEditar("false");
        return null;
    }

    public String prepareView() {
        setCurrent(getSelectedCategoriasred());
        setRenderedGuardar("false");
        setRenderedEditar("false");
        setRenderedPanel("true");
        return null;
    }

    public String prepareCreate() {
        setCurrent(new Categoriasred());
        return "Create";
    }

    public String create() {
        try {            
            getCurrent().setCredAdmining(getCurrentUsuarios().getUsuUsuario());
            getCurrent().setCredAdminfec1(new Date());
            getCurrent().setCredAdminact(getCurrentUsuarios().getUsuUsuario());
            getCurrent().setCredAdminfec2(new Date());
            getFacade().create(getCurrent());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CategoriasredCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        setCurrent(getSelectedCategoriasred());
        setRenderedGuardar("false");
        setRenderedEditar("true");
        setRenderedPanel("true");
        return null;
    }

    public String update() {
        try {
            getCurrent().setCredAdminact(getCurrentUsuarios().getUsuUsuario());
            getCurrent().setCredAdminfec2(new Date());
            getFacade().edit(getCurrent());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CategoriasredUpdated"));
            return "Create";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }
    
     public String cancel() {
        setCurrent(new Categoriasred());
        setRenderedPanel("false");
        return null;
    }

    public String destroy() {
        setCurrent((Categoriasred) getSelectedCategoriasred());
        performDestroy();
        recreateModel();
        return "List";
    }

    private void performDestroy() {
        try {
            getFacade().remove(getCurrent());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CategoriasredDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    public List<Categoriasred> getItems() {
        items = getFacade().findAll();
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public Categoriasred getCategoriasred(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Categoriasred.class)
    public static class CategoriasredControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CategoriasredController controller = (CategoriasredController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "categoriasredController");
            return controller.getCategoriasred(getKey(value));
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
            if (object instanceof Categoriasred) {
                Categoriasred o = (Categoriasred) object;
                return getStringKey(o.getCredCodigo());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Categoriasred.class.getName());
            }
        }

    }

    public Categoriasred getCurrent() {
        return current;
    }

    public void setCurrent(Categoriasred current) {
        this.current = current;
    }

    public Categoriasred getCurrentCategoriasred() {
        return currentCategoriasred;
    }

    public void setCurrentCategoriasred(Categoriasred currentCategoriasred) {
        this.currentCategoriasred = currentCategoriasred;
    }

    public Usuarios getCurrentUsuarios() {
        return currentUsuarios;
    }

    public void setCurrentUsuarios(Usuarios currentUsuarios) {
        this.currentUsuarios = currentUsuarios;
    }

    public List<Categoriasred> getFilterItems() {
        return filterItems;
    }

    public void setFilterItems(List<Categoriasred> filterItems) {
        this.filterItems = filterItems;
    }

    public Categoriasred getSelectedCategoriasred() {
        return selectedCategoriasred;
    }

    public void setSelectedCategoriasred(Categoriasred selectedCategoriasred) {
        this.selectedCategoriasred = selectedCategoriasred;
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

}
