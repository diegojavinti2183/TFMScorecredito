package com.cooperativa.coopintranet.controladores;

import com.cooperativa.coopintranet.entidades.Rolpaginas;
import com.cooperativa.coopintranet.controladores.util.JsfUtil;
import com.cooperativa.coopintranet.controladores.util.PaginationHelper;
import com.cooperativa.coopintranet.entidades.Paginas;
import com.cooperativa.coopintranet.entidades.Roles;
import com.cooperativa.coopintranet.entidades.Usuarios;
import com.cooperativa.coopintranet.servicios.PaginasFacade;
import com.cooperativa.coopintranet.servicios.RolesFacade;
import com.cooperativa.coopintranet.servicios.RolpaginasFacade;

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

@ManagedBean(name = "rolpaginasController")
@ViewScoped
public class RolpaginasController implements Serializable {

    private Rolpaginas current;
    private Usuarios currentUsuarios;
    private List<Rolpaginas> items = null;
    private List<Rolpaginas> filterItems = null;
    private Rolpaginas selectedRolpaginas;
    private Paginas[] SelectedPaginas;
    private List<Paginas> itemspag = null;
    @EJB
    private com.cooperativa.coopintranet.servicios.RolpaginasFacade ejbFacade;
    @EJB
    private com.cooperativa.coopintranet.servicios.PaginasFacade ejbFacade2;
    

    public RolpaginasController() {
        items = new ArrayList<Rolpaginas>();
        itemspag = new ArrayList<Paginas>();
        ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
        LoginUsuariosController referenciaBeanSession = (LoginUsuariosController) contexto.getSessionMap().get("loginUsuariosController");
        currentUsuarios = referenciaBeanSession.getUsuarioLogin();
    }

    @PostConstruct
    public void inicializar() {
        setCurrent(new Rolpaginas());
        setSelectedRolpaginas(new Rolpaginas());
        itemspag = getFacade2().findAll();
        cargarRol();
    }

    private RolpaginasFacade getFacade() {
        return ejbFacade;
    }

    private PaginasFacade getFacade2() {
        return ejbFacade2;
    }

    public String prepareList() {
        recreateModel();
        return "mRolesPaginas";
    }

    public String prepareCreate() {
        setCurrent(new Rolpaginas());
        
        return "mRolesPaginas";
    }

    public String nuevo() {
        setCurrent(new Rolpaginas());
        return null;
    }

    public String create() {
        try {
            for (Paginas pg : getSelectedPaginas()) {
                current.setRolpPagina(pg);
                current.setRolpAdmining(getCurrentUsuarios().getUsuUsuario());
                current.setRolpAdminfec1(new Date());
                current.setRolpAdminact(getCurrentUsuarios().getUsuUsuario());
                current.setRolpAdminfec2(new Date());
                getFacade().create(getCurrent());
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, ResourceBundle.getBundle("/Bundle").getString("MessageInfo"), ResourceBundle.getBundle("/Bundle").getString("RolpaginasCreated")));
            return prepareCreate();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ResourceBundle.getBundle("/Bundle").getString("MessageError"), ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured")));
            return null;
        }
    }

    public void cargarRol() {
        if (current.getRolpRol() != null) {
            items = getFacade().paginasRol(current.getRolpRol());
        }
    }

    public String save() {
        if (getSelectedPaginas() != null) {
            for (Paginas pg : getSelectedPaginas()) {
                System.out.print("holaaaaaaaaaaaaa" + pg.getPagNombre());
            }
        }
        return null;
    }

    public String prepareEdit() {
        setCurrent(getSelectedRolpaginas());
        return null;
    }

    public String prepareView() {
        setCurrent(getSelectedRolpaginas());
        return null;
    }

    public String update() {
        try {
            getCurrent().setRolpAdminact(getCurrentUsuarios().getUsuUsuario());
            getCurrent().setRolpAdminfec2(new Date());
            getFacade().edit(getCurrent());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, ResourceBundle.getBundle("/Bundle").getString("MessageInfo"), ResourceBundle.getBundle("/Bundle").getString("RolpaginasUpdated")));
            return null;
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ResourceBundle.getBundle("/Bundle").getString("MessageError"), ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured")));
            return null;
        }
    }

    public String destroy() {
        setCurrent((Rolpaginas) getSelectedRolpaginas());
        performDestroy();
        recreateModel();
        return null;
    }

    public String cancel() {
        setCurrent(null);
        return null;
    }

    private void performDestroy() {
        try {
            getFacade().remove(getCurrent());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, ResourceBundle.getBundle("/Bundle").getString("MessageInfo"), ResourceBundle.getBundle("/Bundle").getString("RolpaginasDeleted")));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ResourceBundle.getBundle("/Bundle").getString("MessageError"), ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured")));
        }
    }

    public List<Rolpaginas> getItems() {
        return items;
    }

    private void recreateModel() {
        cargarRol();
    }

    public List<Paginas> getItemspag() {
        if (itemspag != null) {
            itemspag = getFacade2().findAll();
        }
        return itemspag;
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public Rolpaginas getCurrent() {
        return current;
    }

    public void setCurrent(Rolpaginas current) {
        this.current = current;
    }

    public Usuarios getCurrentUsuarios() {
        return currentUsuarios;
    }

    public void setCurrentUsuarios(Usuarios currentUsuarios) {
        this.currentUsuarios = currentUsuarios;
    }

    public List<Rolpaginas> getFilterItems() {
        return filterItems;
    }

    public void setFilterItems(List<Rolpaginas> filterItems) {
        this.filterItems = filterItems;
    }

    public Rolpaginas getSelectedRolpaginas() {
        return selectedRolpaginas;
    }

    public void setSelectedRolpaginas(Rolpaginas selectedRolpaginas) {
        this.selectedRolpaginas = selectedRolpaginas;
    }

    public Paginas[] getSelectedPaginas() {
        return SelectedPaginas;
    }

    public void setSelectedPaginas(Paginas[] SelectedPaginas) {
        this.SelectedPaginas = SelectedPaginas;
    }
   
    @FacesConverter(forClass = Rolpaginas.class)
    public static class RolpaginasControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RolpaginasController controller = (RolpaginasController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "rolpaginasController");
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
            if (object instanceof Rolpaginas) {
                Rolpaginas o = (Rolpaginas) object;
                return getStringKey(o.getRolpCodigo());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Rolpaginas.class.getName());
            }
        }
    }
}
