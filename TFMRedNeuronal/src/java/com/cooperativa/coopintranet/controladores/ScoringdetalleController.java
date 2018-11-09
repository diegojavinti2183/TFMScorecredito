package com.cooperativa.coopintranet.controladores;

import com.cooperativa.coopintranet.entidades.Scoringdetalle;
import com.cooperativa.coopintranet.controladores.util.JsfUtil;
import com.cooperativa.coopintranet.controladores.util.PaginationHelper;
import com.cooperativa.coopintranet.servicios.ScoringdetalleFacade;

import java.io.Serializable;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

@Named("scoringdetalleController")
@SessionScoped
public class ScoringdetalleController implements Serializable {

    private Scoringdetalle current;
    private DataModel items = null;
    @EJB
    private com.cooperativa.coopintranet.servicios.ScoringdetalleFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public ScoringdetalleController() {
    }

    public Scoringdetalle getSelected() {
        if (current == null) {
            current = new Scoringdetalle();
            current.setScoringdetallePK(new com.cooperativa.coopintranet.entidades.ScoringdetallePK());
            selectedItemIndex = -1;
        }
        return current;
    }

    private ScoringdetalleFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (Scoringdetalle) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Scoringdetalle();
        current.setScoringdetallePK(new com.cooperativa.coopintranet.entidades.ScoringdetallePK());
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            current.getScoringdetallePK().setDscorScoring(current.getScoringpersonas().getScorNumero());
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ScoringdetalleCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Scoringdetalle) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            current.getScoringdetallePK().setDscorScoring(current.getScoringpersonas().getScorNumero());
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ScoringdetalleUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Scoringdetalle) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ScoringdetalleDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public Scoringdetalle getScoringdetalle(com.cooperativa.coopintranet.entidades.ScoringdetallePK id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Scoringdetalle.class)
    public static class ScoringdetalleControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ScoringdetalleController controller = (ScoringdetalleController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "scoringdetalleController");
            return controller.getScoringdetalle(getKey(value));
        }

        com.cooperativa.coopintranet.entidades.ScoringdetallePK getKey(String value) {
            com.cooperativa.coopintranet.entidades.ScoringdetallePK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new com.cooperativa.coopintranet.entidades.ScoringdetallePK();
            key.setDscorNumero(Integer.parseInt(values[0]));
            key.setDscorScoring(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(com.cooperativa.coopintranet.entidades.ScoringdetallePK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getDscorNumero());
            sb.append(SEPARATOR);
            sb.append(value.getDscorScoring());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Scoringdetalle) {
                Scoringdetalle o = (Scoringdetalle) object;
                return getStringKey(o.getScoringdetallePK());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Scoringdetalle.class.getName());
            }
        }

    }

}
