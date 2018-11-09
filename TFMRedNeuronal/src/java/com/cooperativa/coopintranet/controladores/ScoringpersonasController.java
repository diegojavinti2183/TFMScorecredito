package com.cooperativa.coopintranet.controladores;

import com.cooperativa.coopintranet.entidades.Scoringpersonas;
import com.cooperativa.coopintranet.controladores.util.JsfUtil;
import com.cooperativa.coopintranet.controladores.util.PaginationHelper;
import com.cooperativa.coopintranet.entidades.Conexion;
import com.cooperativa.coopintranet.entidades.Scoringdetalle;
import com.cooperativa.coopintranet.entidades.ScoringdetalleTemp;
import com.cooperativa.coopintranet.entidades.Usuarios;
import com.cooperativa.coopintranet.entidades.Variablesred;
import com.cooperativa.coopintranet.servicios.ScoringdetalleFacade;
import com.cooperativa.coopintranet.servicios.ScoringpersonasFacade;
import com.cooperativa.coopintranet.servicios.VariablesredFacade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
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
import org.neuroph.core.NeuralNetwork;
import org.primefaces.event.RowEditEvent;

@ManagedBean(name = "scoringpersonasController")
@ViewScoped
public class ScoringpersonasController implements Serializable {

    private Scoringpersonas current;
    private Scoringpersonas currentScoringpersonas;
    private ScoringdetalleTemp currentScoringdetalleTemp;
    private Usuarios currentUsuarios;
    private List<Scoringpersonas> items = null;
    private List<Scoringdetalle> itemsScoringdetalle = null;
    private List<ScoringdetalleTemp> itemsScoringdetalleTemp = null;
    private List<Variablesred> itemsVariablesred = null;
    private List<Scoringpersonas> filterItems = null;
    private Scoringpersonas selectedScoringpersonas;
    NeuralNetwork loadedMlPerceptron;
    Conexion conecta;
    @EJB
    private com.cooperativa.coopintranet.servicios.ScoringpersonasFacade ejbFacade;
    @EJB
    private com.cooperativa.coopintranet.servicios.ScoringdetalleFacade ejbFacade2;
    @EJB
    private com.cooperativa.coopintranet.servicios.VariablesredFacade ejbFacade3;

    public ScoringpersonasController() {
        items = new ArrayList<Scoringpersonas>();
        conecta = new Conexion();
        ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
        LoginUsuariosController referenciaBeanSession = (LoginUsuariosController) contexto.getSessionMap().get("loginUsuariosController");
        currentUsuarios = referenciaBeanSession.getUsuarioLogin();
    }

    @PostConstruct
    public void inicializar() {
        setCurrent(new Scoringpersonas());
        current.setScorUsuario(currentUsuarios.getUsuUsuario());
        current.setScorCalificacion("S/E");

    }

    private ScoringpersonasFacade getFacade() {
        return ejbFacade;
    }

    private ScoringdetalleFacade getFacade2() {
        return ejbFacade2;
    }

    private VariablesredFacade getFacade3() {
        return ejbFacade3;
    }

    public void datos() {
        ResultSet rst1 = null;
        itemsScoringdetalleTemp = new ArrayList<ScoringdetalleTemp>();
        String consulta = "select a.cod_socio,"
                + "case when isnull(a.cod_pais_nac,a.cod_pais) = 593 then 0 else 1 end paisOrigen,"
                + "case when isnull(a.cod_provincia_nac,a.cod_provincia) = 1 then 0 else 1 end provinciaOrigen,"
                + "case when isnull(a.cod_canton_nac,a.cod_canton)=101 then 0 else 1 end cantonOrigen,"
                + "case when a.cod_pais = 593 then 0 else 1 end paisRadica,"
                + "case when a.cod_provincia = 1 then 0 else 1 end provinciaRadica,"
                + "case when a.cod_canton = 101 then 0 else 1 end cantonRadica,"
                + "case when a.cod_parroquia=10170 then 0 else 1 end parroquiaRadica,"
                + "a.cod_instruccion socioInstruccion,"
                + "(case when a.cod_tipo_vivienda='P' then 1 when a.cod_tipo_vivienda='N' then 0 when a.cod_tipo_vivienda='F' then 2 when a.cod_tipo_vivienda='A' then 3 end) tipoVivienda,"
                + "(select date_part('year',age(a.fec_ingreso))) socioIngreso,"
                + "(select count(num_credito) from sgf_credito where cod_socio = a.cod_socio ) creditosConcedidos,"
                + "(select count(num_credito) from sgf_credito where cod_socio = a.cod_socio and fec_prestamo<= fec_prestamo-365) creditosUltimoAnio,"//arreglar"
                + "0 creditosnegados,"
                + "a.cod_act_economica actividadEconomica,"
                + "a.cod_act_economica destinoCredito,"//--arreglar y sacar de la tabla del ing lara"
                + "0 clienteClasificacion,"
                + "(select isnull(sum(num_cuotas_pagadas),0) from sgf_credito where cod_socio = a.cod_socio ) numCuotasPagadas,"
                + "(select count(th.cuota) from (select x.cod_cuenta credito,y.num_cuota cuota, max(y.num_dias_mora) dias from sgf_credito x,sgf_credito_saldo_tabla y where x.cod_cuenta = y.cod_cuenta and x.cod_socio = a.cod_socio  group by x.cod_cuenta,y.num_cuota ) th where th.dias > 0) numCuotasMora,"
                + "(select isnull(round(sum(th.dias)/count(th.cuota),2),0) from (select x.cod_cuenta credito,y.num_cuota cuota, max(y.num_dias_mora) dias from sgf_credito x,sgf_credito_saldo_tabla y where x.cod_cuenta = y.cod_cuenta and x.cod_socio = a.cod_socio group by x.cod_cuenta,y.num_cuota ) th where th.dias > 0) mediaMora,"
                + "(select max(x.num_dias_mora) from sgf_credito_saldo_tabla x where x.cod_cuenta in (select cod_cuenta from sgf_credito where cod_socio = a.cod_socio )) moraMayor,"
                + "(select isnull(sum(val_saldo),0) from sgf_credito_saldo where cod_socio = a.cod_socio and val_saldo>0) saldoEntidad,"
                + "0 numEntidades,"
                + "0 saldoEntidades,"
                + "(case when a.sts_sexo='M' then 0 else 1 end) sexo,"
                + "(select date_part('year',age( a.fec_nacimiento ))) edad,"
                + "a.num_cargas_familiares numCargas,"
                + "(case when a.sts_civil = 'S' then 0 when a.sts_civil in ('C','U') then 1 else 2 end) estadoCivil,"
                + "0 situacionlaboral,"
                + "round(((a.val_ingreso_mensual-a.val_gastos_mensuales)/case when a.val_activo = 0 then 1 else a.val_activo end),2) liquidez,"
                + "round((a.val_pasivo/case when (a.val_pasivo+a.val_patrimonio) = 0 then 1 else (a.val_pasivo+a.val_patrimonio) end),2) dependencia,"
                + "round((a.val_pasivo/case when a.val_patrimonio = 0 then 1 else a.val_patrimonio end),2) apalancamiento,"
                + "(0) garantias,"
                + "0 moneda,"
                + "0 monto,"
                + "0 duracion,"
                + "0 interesMensual,"
                + "0 pib,"
                + "0 ipc,"
                + "0 ie "
                + "from sgf_socio a "
                + "where a.num_id = '";
        itemsVariablesred = getFacade3().activosVariablesred();

        int contador = 1;
        if (conecta.conectar()) {
            System.out.println(consulta + current.getScorPersona().getPerIdentificacion() + "'");
            rst1 = conecta.ejecutaQueryConsulta(consulta + current.getScorPersona().getPerIdentificacion() + "'");
            try {
                while (rst1.next()) {
                    for (Variablesred var : itemsVariablesred) {
                        if (rst1.getString(var.getVredId()) != null) {
                            currentScoringdetalleTemp = new ScoringdetalleTemp();
                            getCurrentScoringdetalleTemp().setNumero(contador);
                            getCurrentScoringdetalleTemp().setDscorVariable(var);
                            getCurrentScoringdetalleTemp().setDscorValor(rst1.getBigDecimal(var.getVredId()));
                            //getCurrentScoringdetalleTemp().setDscorValor(BigDecimal.ZERO);
                            contador++;
                            itemsScoringdetalleTemp.add(currentScoringdetalleTemp);
                        }
                    }
                }
            } catch (SQLException ex) {
            } finally {
                conecta.desconectar();
            }
        }
    }
    
    public void onRowEdit(RowEditEvent event) {
        ScoringdetalleTemp scoringdetalleTemp = (ScoringdetalleTemp) event.getObject();
        System.out.println(scoringdetalleTemp.getDscorVariable().getVredNombre());
        //getFacade().edit(scoringdetalleTemp);
       // itemsScoringdetalleTemp.
        FacesMessage msg = new FacesMessage("Variable editada", ((ScoringdetalleTemp) event.getObject()).getDscorVariable().getVredNombre());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edicion cancelada", ((ScoringdetalleTemp) event.getObject()).getDscorVariable().getVredNombre());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public String prepareList() {
        //recreateModel();
        return "List";
    }

    public String nuevo() {
        setCurrent(new Scoringpersonas());
        return null;
    }

    public String prepareView() {
        setCurrent(getSelectedScoringpersonas());
        return null;
    }

    public String prepareCreate() {
        setCurrent(new Scoringpersonas());
        return "Create";
    }

    public String create() {
        try {
            getCurrent().setScorAdmining(getCurrentUsuarios().getUsuUsuario());
            getCurrent().setScorAdminfec1(new Date());
            getCurrent().setScorAdminact(getCurrentUsuarios().getUsuUsuario());
            getCurrent().setScorAdminfec2(new Date());
            //   getFacade()
            getFacade().create(getCurrent());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ScoringpersonasCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public void validate() {
        System.out.println("Testing loaded neural network");
        double[] test = new double[39];
        String resultado = "";
        String calificacion = "";
        loadedMlPerceptron = NeuralNetwork.createFromFile("myMlPerceptron.nnet");
        loadedMlPerceptron.setInput(test);
        loadedMlPerceptron.calculate();

        double[] networkOutput = loadedMlPerceptron.getOutput();

        System.out.print("Input: " + Arrays.toString(test));
        System.out.println(" Output: " + Arrays.toString(networkOutput));
        //}

        for (int i = 0; i < networkOutput.length; i++) {
            if (networkOutput[i] > 0.7) {
                resultado = resultado + "1";
            } else {
                resultado = resultado + "0";
            }
        }
        System.out.println("Resultado " + resultado);
        int rst = Integer.parseInt(resultado, 2);
        switch (rst) {
            case 0:
                calificacion = "A1";
                break;
            case 1:
                calificacion = "A2";
                break;
            case 3:
                calificacion = "A3";
                break;
            case 4:
                calificacion = "B1";
                break;
            case 5:
                calificacion = "B2";
                break;
            case 6:
                calificacion = "C";
                break;
            case 7:
                calificacion = "D";
                break;
            case 8:
                calificacion = "E";
                break;

        }

        current.setScorCalificacion(calificacion);
    }

    public String prepareEdit() {
        setCurrent(getSelectedScoringpersonas());
        return null;
    }

    public String update() {
        try {
            getCurrent().setScorAdminact(getCurrentUsuarios().getUsuUsuario());
            getCurrent().setScorAdminfec2(new Date());
            getFacade().edit(getCurrent());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ScoringpersonasUpdated"));
            return "Create";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String cancel() {
        setCurrent(new Scoringpersonas());
        return null;
    }

    public String destroy() {
        setCurrent((Scoringpersonas) getSelectedScoringpersonas());
        performDestroy();
        //recreateModel();
        return "List";
    }

    private void performDestroy() {
        try {
            getFacade().remove(getCurrent());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ScoringpersonasDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public Scoringpersonas getScoringpersonas(java.lang.Integer id) {
        return ejbFacade.find(id);

    }

    @FacesConverter(forClass = Scoringpersonas.class)
    public static class ScoringpersonasControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ScoringpersonasController controller = (ScoringpersonasController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "scoringpersonasController");
            return controller.getScoringpersonas(getKey(value));
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
            if (object instanceof Scoringpersonas) {
                Scoringpersonas o = (Scoringpersonas) object;
                return getStringKey(o.getScorNumero());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Scoringpersonas.class.getName());
            }
        }

    }

    public Scoringpersonas getCurrent() {
        return current;
    }

    public void setCurrent(Scoringpersonas current) {
        this.current = current;
    }

    public Scoringpersonas getCurrentScoringpersonas() {
        return currentScoringpersonas;
    }

    public void setCurrentScoringpersonas(Scoringpersonas currentScoringpersonas) {
        this.currentScoringpersonas = currentScoringpersonas;
    }

    public Usuarios getCurrentUsuarios() {
        return currentUsuarios;
    }

    public void setCurrentUsuarios(Usuarios currentUsuarios) {
        this.currentUsuarios = currentUsuarios;
    }

    public List<Scoringpersonas> getItems() {
        return items;
    }

    public void setItems(List<Scoringpersonas> items) {
        this.items = items;
    }

    public List<Scoringpersonas> getFilterItems() {
        return filterItems;
    }

    public void setFilterItems(List<Scoringpersonas> filterItems) {
        this.filterItems = filterItems;
    }

    public Scoringpersonas getSelectedScoringpersonas() {
        return selectedScoringpersonas;
    }

    public void setSelectedScoringpersonas(Scoringpersonas selectedScoringpersonas) {
        this.selectedScoringpersonas = selectedScoringpersonas;
    }

    public List<Scoringdetalle> getItemsScoringdetalle() {
        return itemsScoringdetalle;
    }

    public void setItemsScoringdetalle(List<Scoringdetalle> itemsScoringdetalle) {
        this.itemsScoringdetalle = itemsScoringdetalle;
    }

    public List<ScoringdetalleTemp> getItemsScoringdetalleTemp() {
        return itemsScoringdetalleTemp;
    }

    public void setItemsScoringdetalleTemp(List<ScoringdetalleTemp> itemsScoringdetalleTemp) {
        this.itemsScoringdetalleTemp = itemsScoringdetalleTemp;
    }

    public ScoringdetalleTemp getCurrentScoringdetalleTemp() {
        return currentScoringdetalleTemp;
    }

    public void setCurrentScoringdetalleTemp(ScoringdetalleTemp currentScoringdetalleTemp) {
        this.currentScoringdetalleTemp = currentScoringdetalleTemp;
    }

    public List<Variablesred> getItemsVariablesred() {
        return itemsVariablesred;
    }

    public void setItemsVariablesred(List<Variablesred> itemsVariablesred) {
        this.itemsVariablesred = itemsVariablesred;
    }

}
