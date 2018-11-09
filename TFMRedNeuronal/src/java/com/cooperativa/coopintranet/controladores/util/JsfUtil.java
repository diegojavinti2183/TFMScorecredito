package com.cooperativa.coopintranet.controladores.util;

import com.cooperativa.coopintranet.entidades.Conexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;

public class JsfUtil {

    public static SelectItem[] getSelectItems(List<?> entities, boolean selectOne) {
        int size = selectOne ? entities.size() + 1 : entities.size();
        SelectItem[] items = new SelectItem[size];
        int i = 0;
        if (selectOne) {
            items[0] = new SelectItem("", "---");
            i++;
        }
        for (Object x : entities) {
            items[i++] = new SelectItem(x, x.toString());
        }
        return items;
    }

    public static void addErrorMessage(Exception ex, String defaultMsg) {
        String msg = ex.getLocalizedMessage();
        if (msg != null && msg.length() > 0) {
            addErrorMessage(msg);
        } else {
            addErrorMessage(defaultMsg);
        }
    }

    public static Date sumaFechas(Date fecha, Integer diasp) {
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(fecha);
        calendario.add(Calendar.DATE, diasp);
        return calendario.getTime();
    }

    public static boolean validateEmail(String email) {
        String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        // Compiles the given regular expression into a pattern.
        Pattern pattern = Pattern.compile(PATTERN_EMAIL);
        // Match the given input against this pattern
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();

    }

    public static boolean validaCedula(String tipo, String cedula) {
        int total = 0;
        int longitud = cedula.length();
        int longcheck = longitud - 1;
        boolean bandera = false;
        if (tipo.equals("CEDULA")) {
            if (!cedula.equals("") && longitud == 10) {
                for (int i = 0; i < longcheck; i++) {
                    if (i % 2 == 0) {
                        int aux = Integer.parseInt("" + cedula.charAt(i)) * 2;
                        if (aux > 9) {
                            aux -= 9;
                        }
                        total += aux;
                    } else {
                        total += Integer.parseInt("" + cedula.charAt(i)); // parseInt o concatenará en lugar de sumar
                    }
                }
                total = total % 10 > 0 ? 10 - total % 10 : 0;
                if (Integer.parseInt("" + cedula.charAt(longitud - 1)) == total) {
                    bandera = true;
                } else {
                    bandera = false;
                }

            } else {
                bandera = false;
            }
        } else {
            bandera = true;
        }
        return bandera;
    }

    public static String formatoFecha(Date fecha) {
        String cadenaFecha = null, dia, mes, anio;
        if (fecha.getDate() > 9) {
            dia = "" + fecha.getDate();
        } else {
            dia = "0" + fecha.getDate();
        }
        if ((fecha.getMonth() + 1) > 9) {
            mes = "" + (fecha.getMonth() + 1);
        } else {
            mes = "0" + (fecha.getMonth() + 1);
        }
        anio = "" + (fecha.getYear() + 1900);
        cadenaFecha = anio + "-" + mes + "-" + dia;
        return cadenaFecha;
    }

    public static String formatoFechaDDMMYYYY(Date fecha) {
        String cadenaFecha = null, dia, mes, anio;
        if (fecha.getDate() > 9) {
            dia = "" + fecha.getDate();
        } else {
            dia = "0" + fecha.getDate();
        }
        if ((fecha.getMonth() + 1) > 9) {
            mes = "" + (fecha.getMonth() + 1);
        } else {
            mes = "0" + (fecha.getMonth() + 1);
        }
        anio = "" + (fecha.getYear() + 1900);
        cadenaFecha = dia + "-" + mes + "-" + anio;
        return cadenaFecha;
    }

    public static String formatoFechaYYYYMMDD(Date fecha) {
        String cadenaFecha = null, dia, mes, anio;
        if (fecha.getDate() > 9) {
            dia = "" + fecha.getDate();
        } else {
            dia = "0" + fecha.getDate();
        }
        if ((fecha.getMonth() + 1) > 9) {
            mes = "" + (fecha.getMonth() + 1);
        } else {
            mes = "0" + (fecha.getMonth() + 1);
        }
        anio = "" + (fecha.getYear() + 1900);
        cadenaFecha = anio + "-" + mes + "-" + dia;
        return cadenaFecha;
    }

    public static String formatoFechaDDMMYYYY2(Date fecha) {
        String cadenaFecha = null, dia, mes, anio;
        if (fecha.getDate() > 9) {
            dia = "" + fecha.getDate();
        } else {
            dia = "0" + fecha.getDate();
        }
        if ((fecha.getMonth() + 1) > 9) {
            mes = "" + (fecha.getMonth() + 1);
        } else {
            mes = "0" + (fecha.getMonth() + 1);
        }
        anio = "" + (fecha.getYear() + 1900);
        cadenaFecha = dia + "/" + mes + "/" + anio;
        return cadenaFecha;
    }

    public static String formatoFechaArchivo(Date fecha) {
        String cadenaFecha = null, dia, mes, anio;
        if (fecha.getDate() > 9) {
            dia = "" + fecha.getDate();
        } else {
            dia = "0" + fecha.getDate();
        }
        if ((fecha.getMonth() + 1) > 9) {
            mes = "" + (fecha.getMonth() + 1);
        } else {
            mes = "0" + (fecha.getMonth() + 1);
        }
        anio = "" + (fecha.getYear() + 1900);
        cadenaFecha = anio + mes + dia;
        return cadenaFecha;
    }
    
    public static String formatoFechaArchivoIRP(Date fecha) {
        String cadenaFecha = null, dia, mes, anio;
        if ((fecha.getMonth() + 1) > 9) {
            mes = "" + (fecha.getMonth() + 1);
        } else {
            mes = "0" + (fecha.getMonth() + 1);
        }
        anio = "" + (fecha.getYear() + 1900);
        cadenaFecha = mes+anio;
        return cadenaFecha;
    }

    public static String autocompletar(String cadena, int tamano) {
        String result = "";
        if (isNumeric(cadena)) {
            if (cadena.length() < tamano) {
                for (int i = 1; i <= (tamano - cadena.length()); i++) {
                    result = result + "0";
                }
                result = result + cadena;
            } else {
                result = cadena;
            }
        } else {
            result = cadena;
        }
        return result;
    }

    private static boolean isNumeric(String cadena) {
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    public static String eliminaCaracteres(String cadenaEntrada) {
        String cadenaSalida = "";
        // String carcateres[] = {"/", "", "León", "Palencia", "Salamanca", "Segovia", "Soria", "Valladolid", "Zamora"};
        int m = 0;
        if (cadenaEntrada != null) {
            for (int i = 0; i < cadenaEntrada.length(); i++) {
                String caracter = cadenaEntrada.substring(m, i + 1);
                if (caracter.equals("/") || caracter.equals("Ñ") || caracter.equals("Á") || caracter.equals("É") || caracter.equals("Í") || caracter.equals("Ó") || caracter.equals("Ú") || caracter.equals("-")) {
                    System.out.println(cadenaEntrada.substring(m, i + 1));
                    if (caracter.equals("Ñ")) {
                        cadenaSalida = cadenaSalida.concat("N");
                    } else if (caracter.equals("Á")) {
                        cadenaSalida = cadenaSalida.concat("A");
                    } else if (caracter.equals("É")) {
                        cadenaSalida = cadenaSalida.concat("E");
                    } else if (caracter.equals("Í")) {
                        cadenaSalida = cadenaSalida.concat("I");
                    } else if (caracter.equals("Ó")) {
                        cadenaSalida = cadenaSalida.concat("O");
                    } else if (caracter.equals("Ú")) {
                        cadenaSalida = cadenaSalida.concat("U");
                    } else {
                        cadenaSalida = cadenaSalida.concat("");
                    }

                } else {
                    cadenaSalida = cadenaSalida.concat(caracter);
                }
                m++;
            }
        }
        return cadenaSalida;
    }

    public static Date formatoDate(String strFecha) {
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaForm = null;
        if (strFecha != null && !strFecha.equals("")) {
            try {
                fechaForm = formatoDelTexto.parse(strFecha);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }
        return fechaForm;
    }

    public static Date formatoTimestamp(String strFecha) {
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date fechaForm = null;
        try {
            fechaForm = formatoDelTexto.parse(strFecha);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return fechaForm;
    }

    public static Date formatoDateSPI(String strFecha) {
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date fechaForm = null;
        try {
            fechaForm = formatoDelTexto.parse(strFecha);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return fechaForm;
    }

    public static int obtenerUltimoDiaMes(int anio, int mes) {
        Calendar cal = Calendar.getInstance();
        cal.set(anio, mes - 1, 1);
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static String formatoHora(int hora, int minuto) {
        String cadena = null;
        String horas = null;
        String minutos = null;
        if (hora < 10) {
            horas = "0" + hora;
        } else {
            horas = "" + hora;
        }
        if (minuto < 10) {
            minutos = "0" + minuto;
        } else {
            minutos = "" + minuto;
        }
        return horas + ":" + minutos + ":00";
    }

    public static String primeraCadena(String cadena) {
        String primera = "";
        String[] vector = cadena.split(" ");
        if (vector.length > 0) {
            primera = vector[0];
        } else {
            primera = cadena;
        }
        return primera;
    }

    public static String segundaCadena(String cadena) {
        String segunda = "";
        String[] vector = cadena.split(" ");
        if (vector.length > 0) {
            segunda = vector[1];
        } else {
            segunda = "";
        }
        return segunda;
    }

    public static String primeraFechaMesYYYYMMDD(Date fecha) {
        String rmes = "", ranio = "", rdias = "";
        int mes = fecha.getMonth() + 1;
        int anio = fecha.getYear() + 1900;
        int dias = 1;
        if (mes > 9) {
            rmes = "" + mes;
        } else {
            rmes = "0" + mes;
        }
        rdias = "" + dias;
        ranio = "" + anio;

        return ranio + "-" + rmes + "-" + rdias;
    }

    public static String primeraFechaMesYYYYMMDDHHMM(Date fecha) {
        String rmes = "", ranio = "", rdias = "";
        int mes = fecha.getMonth() + 1;
        int anio = fecha.getYear() + 1900;
        int dias = 1;
        if (mes > 9) {
            rmes = "" + mes;
        } else {
            rmes = "0" + mes;
        }
        rdias = "" + dias;
        ranio = "" + anio;

        return ranio + "-" + rmes + "-" + rdias + " 00:00:00";
    }

    public static String diaSemana(Date fecha) {
        String dia = "";
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(fecha);
        int d = calendario.get(Calendar.DAY_OF_WEEK);
        switch (d) {
            case 1:
                dia = "Domingo";
                break;
            case 2:
                dia = "Lunes";
                break;
            case 3:
                dia = "Martes";
                break;
            case 4:
                dia = "Miercoles";
                break;
            case 5:
                dia = "Jueves";
                break;
            case 6:
                dia = "Viernes";
                break;
            case 7:
                dia = "Sabado";
                break;
        }
        return dia;
    }

    public static String ultimaFechaMesYYYYMMDDHHMM(Date fecha) {
        String rmes = "", ranio = "", rdias = "";

        int mes = fecha.getMonth() + 1;
        int anio = fecha.getYear() + 1900;
        int dias = obtenerUltimoDiaMes(anio, mes);
        if (mes > 9) {
            rmes = "" + mes;
        } else {
            rmes = "0" + mes;
        }
        rdias = "" + dias;
        ranio = "" + anio;

        return ranio + "-" + rmes + "-" + rdias + " 23:59:59";
    }

    public static String ultimaFechaMes(String fecha) {
        int mes = 0;
        int anio = 0;
        int dias = 0;
        String rmes = "", ranio = "", rdias = "";
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd-MM-yyyy");
        Date fechaForm = null;
        try {
            fechaForm = formatoDelTexto.parse(fecha);
            mes = fechaForm.getMonth() + 1;
            anio = fechaForm.getYear() + 1900;
            dias = obtenerUltimoDiaMes(anio, mes);
            if (mes > 9) {
                rmes = "" + mes;
            } else {
                rmes = "0" + mes;
            }
            rdias = "" + dias;
            ranio = "" + anio;
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return rdias + "-" + rmes + "-" + ranio;
    }

    public static Integer calcularEdad(String fecha) {
        Date fechaNac = null;
        Integer edad = null;
        if (fecha != null) {
            try {
                fechaNac = new SimpleDateFormat("dd-MM-yyyy").parse(fecha);
            } catch (Exception ex) {
                System.out.println("Error:" + ex);
            }
            Calendar fechaNacimiento = Calendar.getInstance();
            //Se crea un objeto con la fecha actual
            Calendar fechaActual = Calendar.getInstance();
            //Se asigna la fecha recibida a la fecha de nacimiento.
            fechaNacimiento.setTime(fechaNac);
            //Se restan la fecha actual y la fecha de nacimiento
            int año = fechaActual.get(Calendar.YEAR) - fechaNacimiento.get(Calendar.YEAR);
            int mes = fechaActual.get(Calendar.MONTH) - fechaNacimiento.get(Calendar.MONTH);
            int dia = fechaActual.get(Calendar.DATE) - fechaNacimiento.get(Calendar.DATE);
            //Se ajusta el año dependiendo el mes y el día
            if (mes < 0 || (mes == 0 && dia < 0)) {
                año--;
            }
            edad = año;
        } else {
            edad = 0;
        }
        //Regresa la edad en base a la fecha de nacimiento
        return edad;
    }

    public static double redondearDecimales(double valorInicial, int numeroDecimales) {
        double parteEntera, resultado;
        resultado = valorInicial;
        parteEntera = Math.floor(resultado);
        resultado = (resultado - parteEntera) * Math.pow(10, numeroDecimales);
        resultado = Math.round(resultado);
        resultado = (resultado / Math.pow(10, numeroDecimales)) + parteEntera;
        return resultado;
    }

    public static int restaFechasMeses(Date fechaInicio, Date fechaFin) {
        Calendar calendariot = Calendar.getInstance();
        Calendar calendarioi = Calendar.getInstance();
        Calendar calendariof = Calendar.getInstance();
        calendarioi.setTime(fechaInicio);
        calendariof.setTime(fechaFin);
        calendariot.setTimeInMillis(calendariof.getTime().getTime() - calendarioi.getTime().getTime());
        return (int) (calendariot.getTimeInMillis() / (24 * 60 * 60 * 1000));
    }

    public static int restaHoras(Date fechaInicio, Date fechaFin) {
        Calendar calendariot = Calendar.getInstance();
        Calendar calendarioi = Calendar.getInstance();
        Calendar calendariof = Calendar.getInstance();
        calendarioi.setTime(fechaInicio);
        calendariof.setTime(fechaFin);
        calendariot.setTimeInMillis(calendariof.getTime().getTime() - calendarioi.getTime().getTime());
        return (int) (calendariot.getTimeInMillis() / (60 * 60 * 1000));
    }

    public static int sumaHoras(Date fechaInicio, Date fechaFin) {
        Calendar calendariot = Calendar.getInstance();
        Calendar calendarioi = Calendar.getInstance();
        Calendar calendariof = Calendar.getInstance();
        calendarioi.setTime(fechaInicio);
        calendariof.setTime(fechaFin);
        calendariot.setTimeInMillis(calendariof.getTime().getTime() - calendarioi.getTime().getTime());
        return (int) (calendariot.getTimeInMillis() / (60 * 60 * 1000));
    }

    public static int sumarFechasMeses(Date fechaInicio, Date fechaFin) {
        Calendar calendariot = Calendar.getInstance();
        Calendar calendarioi = Calendar.getInstance();
        Calendar calendariof = Calendar.getInstance();
        calendarioi.setTime(fechaInicio);
        calendariof.setTime(fechaFin);
        calendariot.setTimeInMillis(calendariof.getTime().getTime() + calendarioi.getTime().getTime());
        return (int) (calendariot.getTimeInMillis() / (24 * 60 * 60 * 1000));
    }

    public static Date sumarFechaDias(Date fechaInicio, int dia) {
        Calendar calendariot = Calendar.getInstance();
        calendariot.setTime(fechaInicio);
        calendariot.add(Calendar.DAY_OF_YEAR, dia);
        return (Date) calendariot.getTime();
    }

    public static Date restarFechaDias(Date fechaInicio, int dia) {
        Calendar calendariot = Calendar.getInstance();
        calendariot.setTime(fechaInicio);
        calendariot.add(Calendar.DAY_OF_YEAR, (-1 * dia));
        return (Date) calendariot.getTime();
    }

    public static void addErrorMessages(List<String> messages) {
        for (String message : messages) {
            addErrorMessage(message);
        }
    }

    public static void addErrorMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }

    public static void addSuccessMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg);
        FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);
    }

    public static String getRequestParameter(String key) {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(key);
    }

    public static Object getObjectFromRequestParameter(String requestParameterName, Converter converter, UIComponent component) {
        String theId = JsfUtil.getRequestParameter(requestParameterName);
        return converter.getAsObject(FacesContext.getCurrentInstance(), component, theId);
    }

    public static long generaRamdomicosSRI() {
        String valor = "";
        long resultado = 0;
        int val = 0;
        int[] vec = new int[8];
        int[] aux = new int[8];
        valor = String.valueOf(new Random().nextLong()).substring(8);
        for (int i = 0; i < 8; i++) {
            vec[i] = Integer.parseInt(valor.substring(i, i + 1));
        }
        aux[0] = vec[0] * 3;
        aux[1] = vec[1] * 2;
        aux[2] = vec[2] * 7;
        aux[3] = vec[3] * 6;
        aux[4] = vec[4] * 5;
        aux[5] = vec[5] * 4;
        aux[6] = vec[6] * 3;
        aux[7] = vec[7] * 2;
        for (int i = 0; i < 8; i++) {
            val = val + aux[i];
        }
        val = val % 11;
        val = 11 - val;

        if (val == 11) {
            val = 0;
        } else if (val == 10) {
            val = 1;
        }
        return Long.parseLong(valor.concat(String.valueOf(val)));
    }

    public static String obtenerAnio(Date fecha) {
        String anio = "";
        anio = "" + (fecha.getYear() + 1900);
        return anio;
    }

    public static String obtenerMes(Date fecha) {
        int mess = fecha.getMonth() + 1;
        String mes;
        if (mess > 9) {
            mes = "" + mess;
        } else {
            mes = "0" + mess;
        }
        return mes;
    }

    ////////////////////////////////////////////////////////////
    public static int numeroComprobante(Conexion conecta, String tipo) {
        ResultSet rst1 = null;
        int numero = 0;
        try {
            rst1 = conecta.ejecutaQueryConsulta("select num_numero from scg_tipo_cmp where cod_tipo_cmp = '" + tipo + "'");
            while (rst1.next()) {
                numero = rst1.getInt(1) + 1;
            }
            rst1.close();
        } catch (SQLException ex) {

        }

        return numero;
    }

    public static void actualizaNumeroComprobante(Conexion conecta, int numeroComprobante, String tipo) {
        int numero = numeroComprobante;
        conecta.ejecutaQueryActualiza("update scg_tipo_cmp set num_numero=" + numero + "  where cod_tipo_cmp = '" + tipo + "'");
        conecta.commitTransaccion();
    }

    public static PrintService findPrintService(String printerName) {
        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
        for (PrintService printService : printServices) {
            if (printService.getName().trim().equals(printerName)) {
                return printService;
            }
        }
        return null;
    }
}
