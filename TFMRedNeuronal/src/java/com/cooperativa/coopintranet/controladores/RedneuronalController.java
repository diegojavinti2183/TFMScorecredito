package com.cooperativa.coopintranet.controladores;

import com.cooperativa.coopintranet.entidades.Redneuronal;
import com.cooperativa.coopintranet.controladores.util.JsfUtil;
import com.cooperativa.coopintranet.controladores.util.PaginationHelper;
import com.cooperativa.coopintranet.entidades.DatasetTemp;
import com.cooperativa.coopintranet.entidades.Usuarios;
import com.cooperativa.coopintranet.entidades.Variablesred;
import com.cooperativa.coopintranet.servicios.RedneuronalFacade;
import com.cooperativa.coopintranet.servicios.VariablesredFacade;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javax.swing.JFileChooser;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.core.events.LearningEvent;
import org.neuroph.core.events.LearningEventListener;
import org.neuroph.core.learning.LearningRule;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.BackPropagation;
import org.neuroph.util.TransferFunctionType;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

@ManagedBean(name = "redneuronalController")
@ViewScoped
public class RedneuronalController implements Serializable , LearningEventListener {

    public List<DatasetTemp> getFilterItemsDatasetTemp() {
        return filterItemsDatasetTemp;
    }

    public void setFilterItemsDatasetTemp(List<DatasetTemp> filterItemsDatasetTemp) {
        this.filterItemsDatasetTemp = filterItemsDatasetTemp;
    }

    private Redneuronal current;
    private Redneuronal currentRedneuronal;
    private DatasetTemp currentDatasetTemp;
    private Usuarios currentUsuarios;
    private List<Redneuronal> items = null;
    private List<Variablesred> itemsVariablesred = null;
    private List<Redneuronal> filterItems = null;
    private List<DatasetTemp> itemsDatasetTemp;
    private List<DatasetTemp> filterItemsDatasetTemp;
    private List<DatasetTemp> itemsDatasetTestTemp;
    private List<DatasetTemp> filterItemsDatasetTestTemp;
    private Redneuronal selectedRedneuronal;
    private List<Double[]> listaDatos;
    private ArrayList<double[]> inputs = new ArrayList<double[]>();
    private ArrayList<Double[]> inputsX = new ArrayList<Double[]>();
    private ArrayList<double[]> trainData = new ArrayList<double[]>();
    private ArrayList<double[]> testData = new ArrayList<double[]>();
    DataSet trainingSet;
    MultiLayerPerceptron myMlPerceptron;
    BackPropagation back;
    LearningRule learningRule;
    NeuralNetwork loadedMlPerceptron;
    @EJB
    private com.cooperativa.coopintranet.servicios.RedneuronalFacade ejbFacade;
    @EJB
    private com.cooperativa.coopintranet.servicios.VariablesredFacade ejbFacade2;

    public RedneuronalController() {
        items = new ArrayList<Redneuronal>();
        ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
        LoginUsuariosController referenciaBeanSession = (LoginUsuariosController) contexto.getSessionMap().get("loginUsuariosController");
        currentUsuarios = referenciaBeanSession.getUsuarioLogin();

    }

    @PostConstruct
    public void inicializar() {
        setCurrent(new Redneuronal());
        if (getFacade().cargar() != null) {
            current = getFacade().cargar();

        }
        try {
            loadFile();
        } catch (IOException ex) {
            Logger.getLogger(RedneuronalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private RedneuronalFacade getFacade() {
        return ejbFacade;
    }

    private VariablesredFacade getFacade2() {
        return ejbFacade2;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String nuevo() {
        setCurrent(new Redneuronal());
        return null;
    }

    public String prepareView() {
        setCurrent(getSelectedRedneuronal());
        return null;
    }

    public String prepareCreate() {
        setCurrent(new Redneuronal());
        return "Create";
    }

    public String create() {
        try {
            getFacade().actualizaEstado();
            getCurrent().setRedEstado(true);
            getCurrent().setRedAdmining(getCurrentUsuarios().getUsuUsuario());
            getCurrent().setRedAdminfec1(new Date());
            getCurrent().setRedAdminact(getCurrentUsuarios().getUsuUsuario());
            getCurrent().setRedAdminfec2(new Date());
            getFacade().create(getCurrent());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("RedneuronalCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("RedneuronalErrorOccured"));
            return null;
        }
    }

    public void training() {
        System.out.println(trainData.get(0).length);
        System.out.println(testData.get(0).length);
        trainingSet = new DataSet(trainData.get(0).length, testData.get(0).length);
        for (int j = 0; j < trainData.size(); j++) {
            trainingSet.addRow(new DataSetRow(trainData.get(j), testData.get(j)));
        }

        // create multi layer perceptron
        //MultiLayerPerceptron myMlPerceptron = new MultiLayerPerceptron(TransferFunctionType.SIGMOID, 2, 3, 1);
        myMlPerceptron = new MultiLayerPerceptron(TransferFunctionType.SIGMOID, current.getRedNentrada(), current.getRedNcapasocultas(), current.getRedNsalida());
        back = new BackPropagation();
        back.setMaxIterations(current.getRedIteraciones());
        back.setMaxError(current.getRedMaxerror().doubleValue());

        System.out.println("error " + back.getMaxError());
        //myMlPerceptron.setLearningRule(new BackPropagation());
        myMlPerceptron.setLearningRule(back);

        learningRule = myMlPerceptron.getLearningRule();
        learningRule.addListener(this);

        // learn the training set
        System.out.println("Entrenamiento Red Neuronal..");
        myMlPerceptron.learn(trainingSet);

        // test perceptron
        System.out.println("Prueba Red Neuronal Entrenada");
        testNeuralNetwork(myMlPerceptron, trainingSet);

        // save trained neural network
        myMlPerceptron.save("myMlPerceptron.nnet");

        // load saved neural network
        loadedMlPerceptron = NeuralNetwork.createFromFile("myMlPerceptron.nnet");

        // test loaded neural network
        // testNeuralNetwork(loadedMlPerceptron, trainingSet);
       
    }
    
    public static void testNeuralNetwork(NeuralNetwork neuralNet, DataSet testSet) {
        for (DataSetRow testSetRow : testSet.getRows()) {
            neuralNet.setInput(testSetRow.getInput());
            neuralNet.calculate();
            double[] networkOutput = neuralNet.getOutput();

            System.out.print("Input: " + Arrays.toString(testSetRow.getInput()));
            System.out.println(" Output: " + Arrays.toString(networkOutput));
        }
    }

    @Override
    public void handleLearningEvent(LearningEvent event) {
        BackPropagation bp = (BackPropagation) event.getSource();
        if (event.getEventType() != LearningEvent.Type.LEARNING_STOPPED) {
            System.out.println(bp.getCurrentIteration() + ". iteration : " + bp.getTotalNetworkError());
        }
    }

    public String prepareEdit() {
        setCurrent(getSelectedRedneuronal());
        return null;
    }

    public String update() {
        try {
            getCurrent().setRedAdminact(getCurrentUsuarios().getUsuUsuario());
            getCurrent().setRedAdminfec2(new Date());
            getFacade().edit(getCurrent());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("RedneuronalUpdated"));
            return "Create";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    private void loadFile2() {
        try {
            File archivo = new File("C:\\archivo.txt");
            FileReader ruta = new FileReader(archivo);
            BufferedReader br = new BufferedReader(ruta);

            String line = br.readLine();
            while (line != null) {
                // Split by space or tab
                String[] lineSplit = line.split("\\s+");
                // Remove empty elements
                //lineSplit = Arrays.stream(lineSplit).filter(s -> (s != null && s.length() > 0)).toArray(String[]::new);
                double[] numbers = new double[lineSplit.length];
                Double[] numbersX = new Double[lineSplit.length];

                for (int i = 0; i < lineSplit.length; i++) {
                    numbers[i] = Double.parseDouble(lineSplit[i]);
                    numbersX[i] = Double.parseDouble(lineSplit[i]);
                }
                getInputs().add(numbers);//adhiere el vector en en arraylist de entrada
                getInputsX().add(numbersX);
                line = br.readLine();
            }

//            ArrayList<String> header = new ArrayList<>();
//            for (int i = 1; i < getInputs().get(0).length - 3; i++) {
//                header.add("x" + i);
//            }
//            header.add("y1");
//            header.add("y2");
//            header.add("y3");
            //trainTableModel.setColumnIdentifiers(header.toArray());
            //testTableModel.setColumnIdentifiers(header.toArray());
            for (double[] x : getInputs()) {
                getTrainData().add(Arrays.copyOfRange(x, 0, x.length - 4));
                getTestData().add(Arrays.copyOfRange(x, x.length - 4, x.length));
                //trainTableModel.addRow(inputsX);
            }

//            for (Double[] x : getInputsX()) {
//               // trainTableModel.addRow(x);
//            }
            //trainTable.setModel(trainTableModel);
            //testTable.setModel(testTableModel);
            for (double[] x : getTrainData()) {
                for (int i = 0; i < x.length; i++) {
                    System.out.print(" Entradas " + x[i]);
                }
                System.out.print("\n");
            }

            for (double[] x : getTestData()) {
                for (int i = 0; i < x.length; i++) {
                    System.out.print(" Salidas " + x[i]);

                }
                System.out.print("\n");
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private Double round(Double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public String cancel() {
        setCurrent(new Redneuronal());
        return null;
    }

    public String destroy() {
        setCurrent((Redneuronal) getSelectedRedneuronal());
        performDestroy();
        recreateModel();
        return "List";
    }

    private void performDestroy() {
        try {
            getFacade().remove(getCurrent());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("RedneuronalDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void loadFile() throws IOException {
        itemsDatasetTemp = new ArrayList<DatasetTemp>();
        ArrayList<double[]> datosEntrada = new ArrayList<double[]>();
        BufferedReader br = null;
        try {
            File loadedFile = new File("C:\\archivo.txt");

            br = new BufferedReader(new FileReader(loadedFile));
            String line = br.readLine();
            while (line != null) {
                // Split by space or tab
                String[] lineSplit = line.split("\\s+");
                double[] numbersX = new double[current.getRedNentrada() + current.getRedNsalida()];

                for (int i = 0; i < lineSplit.length; i++) {
                    numbersX[i] = Double.parseDouble(lineSplit[i]);
                }

                currentDatasetTemp = new DatasetTemp(numbersX[0], numbersX[1], numbersX[2], numbersX[3], numbersX[4], numbersX[5], numbersX[6], numbersX[7], numbersX[8], numbersX[9], numbersX[10], numbersX[11], numbersX[12], numbersX[13], numbersX[14], numbersX[15], numbersX[16], numbersX[17], numbersX[18], numbersX[19], numbersX[20], numbersX[21], numbersX[22], numbersX[23], numbersX[24], numbersX[25], numbersX[26], numbersX[27], numbersX[28], numbersX[29], numbersX[30], numbersX[31], numbersX[32], numbersX[33], numbersX[34], numbersX[35], numbersX[36], numbersX[37], numbersX[38], numbersX[39], numbersX[40], numbersX[41], numbersX[42]);
                itemsDatasetTemp.add(currentDatasetTemp);
                datosEntrada.add(numbersX);
                line = br.readLine();
            }
            
            for (double[] x : datosEntrada) {
                getTrainData().add(Arrays.copyOfRange(x, 0, x.length - 4));
                getTestData().add(Arrays.copyOfRange(x, x.length - 4, x.length));
                //trainTableModel.addRow(inputsX);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RedneuronalController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(RedneuronalController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
//        return datosEntrada;
    }

    public void onRowCancel(RowEditEvent event) {

    }

    public List<Redneuronal> getItems() {
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

    public Redneuronal getRedneuronal(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Redneuronal.class)
    public static class RedneuronalControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RedneuronalController controller = (RedneuronalController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "redneuronalController");
            return controller.getRedneuronal(getKey(value));
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
            if (object instanceof Redneuronal) {
                Redneuronal o = (Redneuronal) object;
                return getStringKey(o.getRedSecuencia());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Redneuronal.class.getName());
            }
        }

    }

    public Redneuronal getCurrent() {
        return current;
    }

    public void setCurrent(Redneuronal current) {
        this.current = current;
    }

    public Redneuronal getCurrentRedneuronal() {
        return currentRedneuronal;
    }

    public void setCurrentRedneuronal(Redneuronal currentRedneuronal) {
        this.currentRedneuronal = currentRedneuronal;
    }

    public Usuarios getCurrentUsuarios() {
        return currentUsuarios;
    }

    public void setCurrentUsuarios(Usuarios currentUsuarios) {
        this.currentUsuarios = currentUsuarios;
    }

    public List<Redneuronal> getFilterItems() {
        return filterItems;
    }

    public void setFilterItems(List<Redneuronal> filterItems) {
        this.filterItems = filterItems;
    }

    public Redneuronal getSelectedRedneuronal() {
        return selectedRedneuronal;
    }

    public void setSelectedRedneuronal(Redneuronal selectedRedneuronal) {
        this.selectedRedneuronal = selectedRedneuronal;
    }

    public List<DatasetTemp> getItemsDatasetTemp() {
        return itemsDatasetTemp;
    }

    public void setItemsDatasetTemp(List<DatasetTemp> itemsDatasetTemp) {
        this.itemsDatasetTemp = itemsDatasetTemp;
    }

    public List<Double[]> getListaDatos() {
        return listaDatos;
    }

    public void setListaDatos(List<Double[]> listaDatos) {
        this.listaDatos = listaDatos;
    }

    public ArrayList<double[]> getInputs() {
        return inputs;
    }

    public void setInputs(ArrayList<double[]> inputs) {
        this.inputs = inputs;
    }

    public ArrayList<Double[]> getInputsX() {
        return inputsX;
    }

    public void setInputsX(ArrayList<Double[]> inputsX) {
        this.inputsX = inputsX;
    }

    public ArrayList<double[]> getTrainData() {
        return trainData;
    }

    public void setTrainData(ArrayList<double[]> trainData) {
        this.trainData = trainData;
    }

    public ArrayList<double[]> getTestData() {
        return testData;
    }

    public void setTestData(ArrayList<double[]> testData) {
        this.testData = testData;
    }

    public List<DatasetTemp> getItemsDatasetTestTemp() {
        return itemsDatasetTestTemp;
    }

    public void setItemsDatasetTestTemp(List<DatasetTemp> itemsDatasetTestTemp) {
        this.itemsDatasetTestTemp = itemsDatasetTestTemp;
    }

    public List<DatasetTemp> getFilterItemsDatasetTestTemp() {
        return filterItemsDatasetTestTemp;
    }

    public void setFilterItemsDatasetTestTemp(List<DatasetTemp> filterItemsDatasetTestTemp) {
        this.filterItemsDatasetTestTemp = filterItemsDatasetTestTemp;
    }

}
