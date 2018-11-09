package com.cooperativa.coopintranet.controladores;

import com.cooperativa.coopintranet.entidades.Variablesred;
import com.cooperativa.coopintranet.controladores.util.JsfUtil;
import com.cooperativa.coopintranet.controladores.util.PaginationHelper;
import com.cooperativa.coopintranet.entidades.Usuarios;
import com.cooperativa.coopintranet.servicios.VariablesredFacade;

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

@ManagedBean(name ="variablesredController")
@ViewScoped
public class VariablesredController implements Serializable {

    private Variablesred current;
    private Variablesred currentVariablesred;
    private Usuarios currentUsuarios; 
    private List<Variablesred> items = null;
    private List<Variablesred> filterItems = null;
    private Variablesred selectedVariablesred;
    private String renderedPanel = "false";
    private String renderedGuardar = "false";
    private String renderedEditar = "false";
    @EJB
    private com.cooperativa.coopintranet.servicios.VariablesredFacade ejbFacade;
    
    public VariablesredController() {
        items = new ArrayList<Variablesred>();
        ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
        LoginUsuariosController referenciaBeanSession = (LoginUsuariosController) contexto.getSessionMap().get("loginUsuariosController");
        currentUsuarios = referenciaBeanSession.getUsuarioLogin();
    }

    private VariablesredFacade getFacade() {
        return ejbFacade;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }
    
    public String nuevo() {
        setCurrent(new Variablesred());
        setRenderedPanel("true");
        setRenderedGuardar("true");
        setRenderedEditar("false");
        return null;
    }

    public String prepareView() {
        setCurrent(getSelectedVariablesred());
        setRenderedGuardar("false");
        setRenderedEditar("false");
        setRenderedPanel("true");
        return null;
    }

    public String prepareCreate() {
        setCurrent(new Variablesred());
        return "Create";
    }

    public String create() {
        try {            
            getCurrent().setVredAdmining(getCurrentUsuarios().getUsuUsuario());
            getCurrent().setVredAdminfec1(new Date());
            getCurrent().setVredAdminact(getCurrentUsuarios().getUsuUsuario());
            getCurrent().setVredAdminfec2(new Date());
            getFacade().create(getCurrent());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("VariablesredCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        setCurrent(getSelectedVariablesred());
        setRenderedGuardar("false");
        setRenderedEditar("true");
        setRenderedPanel("true");
        return null;
    }

    public String update() {
        try {
            getCurrent().setVredAdminact(getCurrentUsuarios().getUsuUsuario());
            getCurrent().setVredAdminfec2(new Date());
            getFacade().edit(getCurrent());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("VariablesredUpdated"));
            return "Create";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }
    
     public String cancel() {
        setCurrent(new Variablesred());
        setRenderedPanel("false");
        return null;
    }

    public String destroy() {
        setCurrent((Variablesred) getSelectedVariablesred());
        performDestroy();
        recreateModel();
        return "List";
    }

    private void performDestroy() {
        try {
            getFacade().remove(getCurrent());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("VariablesredDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    public List<Variablesred> getItems() {
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

    public Variablesred getVariablesred(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Variablesred.class)
    public static class VariablesredControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            VariablesredController controller = (VariablesredController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "variablesredController");
            return controller.getVariablesred(getKey(value));
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
            if (object instanceof Variablesred) {
                Variablesred o = (Variablesred) object;
                return getStringKey(o.getVredCodigo());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Variablesred.class.getName());
            }
        }

    }

    public Variablesred getCurrent() {
        return current;
    }

    public void setCurrent(Variablesred current) {
        this.current = current;
    }

    public Variablesred getCurrentVariablesred() {
        return currentVariablesred;
    }

    public void setCurrentVariablesred(Variablesred currentVariablesred) {
        this.currentVariablesred = currentVariablesred;
    }

    public Usuarios getCurrentUsuarios() {
        return currentUsuarios;
    }

    public void setCurrentUsuarios(Usuarios currentUsuarios) {
        this.currentUsuarios = currentUsuarios;
    }

    public List<Variablesred> getFilterItems() {
        return filterItems;
    }

    public void setFilterItems(List<Variablesred> filterItems) {
        this.filterItems = filterItems;
    }

    public Variablesred getSelectedVariablesred() {
        return selectedVariablesred;
    }

    public void setSelectedVariablesred(Variablesred selectedVariablesred) {
        this.selectedVariablesred = selectedVariablesred;
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
