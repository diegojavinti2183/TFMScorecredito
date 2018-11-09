package com.cooperativa.coopintranet.controladores;

import com.cooperativa.coopintranet.entidades.Parametros;
import com.cooperativa.coopintranet.controladores.util.JsfUtil;
import com.cooperativa.coopintranet.controladores.util.PaginationHelper;
import com.cooperativa.coopintranet.entidades.Usuarios;
import com.cooperativa.coopintranet.servicios.ParametrosFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
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

@ManagedBean(name = "parametrosController")
@ViewScoped
public class ParametrosController implements Serializable {

    private Parametros current;
    private Usuarios currentUsuarios;
    private List<Parametros> items = null;
    private List<Parametros> filterItems = null;
    private Parametros selectedParametros;
    private String renderedPanel = "false";
    private String renderedGuardar = "false";
    private String renderedEditar = "false";
    @EJB
    private com.cooperativa.coopintranet.servicios.ParametrosFacade ejbFacade;
    

    public ParametrosController() {
        items = new ArrayList<Parametros>();
        ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
        LoginUsuariosController referenciaBeanSession = (LoginUsuariosController) contexto.getSessionMap().get("loginUsuariosController");
        currentUsuarios = referenciaBeanSession.getUsuarioLogin();
    }

    @PostConstruct
    public void inicializar() {
        setCurrent(new Parametros());
        setSelectedParametros(new Parametros());
        //cargarEstado();
    }

    private ParametrosFacade getFacade() {
        return ejbFacade;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }
    
    public String nuevo() {
        setCurrent(new Parametros());
        setRenderedPanel("true");
        setRenderedGuardar("true");
        setRenderedEditar("false");
        return null;
    }

    public String prepareView() {
        setCurrent(getSelectedParametros());
        setRenderedGuardar("false");
        setRenderedEditar("false");
        setRenderedPanel("true");
        return null;
    }

    public String prepareCreate() {
        setCurrent(new Parametros());
        return "Create";
    }

    public String create() {
        try {
            getCurrent().setParAdmining(getCurrentUsuarios().getUsuUsuario());
            getCurrent().setParAdminfec1(new Date());
            getCurrent().setParAdminact(getCurrentUsuarios().getUsuUsuario());
            getCurrent().setParAdminfec2(new Date());
            getFacade().create(getCurrent());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ParametrosCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        setCurrent(getSelectedParametros());
        setRenderedGuardar("false");
        setRenderedEditar("true");
        setRenderedPanel("true");
        return null;
    }

    public String update() {
        try {
            getCurrent().setParAdminact(getCurrentUsuarios().getUsuUsuario());
            getCurrent().setParAdminfec2(new Date());
            getFacade().edit(getCurrent());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ParametrosUpdated"));
            return "Create";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }
    
     public String cancel() {
        setCurrent(new Parametros());
        setRenderedPanel("false");
        return null;
    }

    public String destroy() {
        setCurrent((Parametros) getSelectedParametros());
        performDestroy();
        recreateModel();
        return "List";
    }

    private void performDestroy() {
        try {
            getFacade().remove(getCurrent());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ParametrosDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    public List<Parametros> getItems() {
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

    public Parametros getCurrent() {
        return current;
    }

    public void setCurrent(Parametros current) {
        this.current = current;
    }

    public List<Parametros> getFilterItems() {
        return filterItems;
    }

    public void setFilterItems(List<Parametros> filterItems) {
        this.filterItems = filterItems;
    }

    public Parametros getSelectedParametros() {
        return selectedParametros;
    }

    public void setSelectedParametros(Parametros selectedParametros) {
        this.selectedParametros = selectedParametros;
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

    public Usuarios getCurrentUsuarios() {
        return currentUsuarios;
    }

    public void setCurrentUsuarios(Usuarios currentUsuarios) {
        this.currentUsuarios = currentUsuarios;
    }

    @FacesConverter(forClass = Parametros.class)
    public static class ParametrosControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ParametrosController controller = (ParametrosController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "parametrosController");
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
            if (object instanceof Parametros) {
                Parametros o = (Parametros) object;
                return getStringKey(o.getParCodigo());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Parametros.class.getName());
            }
        }
    }
}
