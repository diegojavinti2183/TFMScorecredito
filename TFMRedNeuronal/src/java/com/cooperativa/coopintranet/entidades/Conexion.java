/*
 * Driver.java
 *
 * Created on 20 de octubre de 2006, 10:22
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.cooperativa.coopintranet.entidades;

/**
 *
 * @author Usuario
 */
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;
import java.util.*;
import java.util.Vector;
import javax.swing.*;
import java.io.*;

public class Conexion {

    private Connection conexion;
    public Statement stmt;
    public ResultSet rs;
    public int upt;
    FileOutputStream fos = null;

    /** Creates a new instance of Driver */
    public Conexion() {
    }

    public boolean conectar() {
        boolean result = false;
        String url = "";
        try {
            
            Class.forName("org.postgresql.Driver");//Cargamos el driver
            url = "jdbc:postgresql://192.168.10.151:5432/postgres";
            //url = "jdbc:postgresql://192.168.10.30:5432/test";
            setConexion(DriverManager.getConnection(url, "postgres", "Admin12345"));
            //setConexion(DriverManager.getConnection(url, "postgres", "coacsanjose"));
            conexion.setAutoCommit(false);            
            result = true;
        } catch (SQLException ex) {
            System.out.println("SQLExceptions: " + ex.getMessage());
           // JOptionPane.showMessageDialog(asi, ex.getMessage() + "\nComunicarse con su Administrador", "Error", JOptionPane.ERROR_MESSAGE);
            result = false;
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
           // JOptionPane.showMessageDialog(asi, e.getMessage() + "\nComunicarse con su Administrador", "Error", JOptionPane.ERROR_MESSAGE);
            result = false;
        }
        return result;
    }
    
    

    public boolean desconectar() {
        try {
            getConexion().close();
            return true;
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            return false;
        } catch (ConcurrentModificationException e1) {
            System.out.println(e1.getMessage());
            return false;
        } catch (java.lang.NullPointerException e2) {
            System.out.println(e2.getMessage());
            return false;
        }
    }

    public boolean insertar(String cadena) {
        try {
            stmt = getConexion().createStatement();
            stmt.execute(cadena);
            stmt.close();
            // JOptionPane.showMessageDialog(rol, "Datos grabados satisfactoriamente");
            return true;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println(cadena);
          //  JOptionPane.showMessageDialog(asi, e.getMessage() + "\nComunicarse con su Administrador", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    /**
     *
     * @param usuario
     * @param fecha
     * @param empresa
     * @param errores
     * @return
     */
    public String procedimientoTransaccionesAutomaticas(int usuario,java.util.Date fecha,int empresa,String errores) {
        String result = null;
        try {            
            CallableStatement cst = getConexion().prepareCall("{call P_GENERA_TRANSACCIONES_AUTO(?,?,?,?)}");
            cst.setInt(1, usuario);
            cst.setDate(2, new java.sql.Date(fecha.getYear()+1900, fecha.getMonth()+1, fecha.getDate()));
            cst.setInt(3, empresa);
            cst.registerOutParameter(4, java.sql.Types.VARCHAR);
            cst.execute();
            result = cst.getString(4);           
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
          //  JOptionPane.showMessageDialog(asi, e.getMessage() + "\nComunicarse con su Administrador", "Error", JOptionPane.ERROR_MESSAGE);
            
        }
         return result;
    }

    public void commitTransaccion(){
        try {
            conexion.commit();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public void rollbackTransaccion(){
        try {
            conexion.rollback();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getDato(String consulta) {
        String dato = null;
        try {
            stmt = getConexion().createStatement();
            rs = stmt.executeQuery(consulta);
            while (rs.next()) {
                dato = rs.getString(1);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            dato = null;
        
        }
        return dato;
    }

    @SuppressWarnings("empty-statement")
    public String getCadena(String consulta) {
        String valor = "";
        try {
            stmt = getConexion().createStatement();
            rs = stmt.executeQuery(consulta);
            while (rs.next()) {
                valor = rs.getString(1);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(consulta);
          //  JOptionPane.showMessageDialog(asi, e.getMessage() + "\nComunicarse con su Administrador", "Error", JOptionPane.ERROR_MESSAGE);

        } catch (java.lang.NullPointerException e1) {
            System.out.println(e1.getMessage());
            System.out.println(consulta);
          //  JOptionPane.showMessageDialog(asi, e1.getMessage() + "\nComunicarse con su Administrador", "Error", JOptionPane.ERROR_MESSAGE);
        }
        ;
        return valor;
    }

    @SuppressWarnings("empty-statement")
    public Vector getDatos(String consulta, int col) {
        Vector datos = new Vector(col);
        Vector columnas = null;
        try {
            stmt = getConexion().createStatement();
            rs = stmt.executeQuery(consulta);
            // datos= new Vector();
            while (rs.next()) {
                columnas = new Vector();
                for (int i = 0; i < col; i++) {
                    int a = i + 1;
                    columnas.addElement(rs.getString(a));
                }
                datos.addElement(columnas);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println(consulta);
          //  JOptionPane.showMessageDialog(asi, e.getMessage() + "\nComunicarse con su Administrador", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (java.lang.NullPointerException e1) {
            System.out.println(e1.getMessage());
            System.out.println(consulta);
          //  JOptionPane.showMessageDialog(asi, e1.getMessage() + "\nComunicarse con su Administrador", "Error", JOptionPane.ERROR_MESSAGE);
        }
        ;
        return datos;
    }

    @SuppressWarnings("empty-statement")
    public Vector getFila(String consulta, int columna) {
        Vector fila = new Vector(columna);
        try {
            stmt = getConexion().createStatement();
            rs = stmt.executeQuery(consulta);
            while (rs.next()) {
                for (int i = 1; i <= columna; i++) {
                    fila.add(i - 1, rs.getString(i));
                }
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println(consulta);
          //  JOptionPane.showMessageDialog(asi, e.getMessage() + "\nComunicarse con su Administrador", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (java.lang.NullPointerException e1) {
            System.out.println(e1.getMessage());
            System.out.println(consulta);
        }
        ;
        return fila;
    }

    @SuppressWarnings("empty-statement")
    public Vector getColumna(String consulta) {
        Vector columna = new Vector();
        try {
            stmt = getConexion().createStatement();
            rs = stmt.executeQuery(consulta);
            while (rs.next()) {
                columna.add(rs.getString(1));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println(consulta);
         //   JOptionPane.showMessageDialog(asi, e.getMessage() + "\nComunicarse con su Administrador", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (java.lang.NullPointerException e1) {
            System.out.println(e1.getMessage());
            System.out.println(consulta);
         //   JOptionPane.showMessageDialog(asi, e1.getMessage() + "\nComunicarse con su Administrador", "Error", JOptionPane.ERROR_MESSAGE);
        }
        ;
        return columna;
    }

    public Vector ejecutarQuery(String consulta) {
        ResultSet resulset = null;
        boolean bandera = false;
        Vector result = null;
        try {
            stmt = getConexion().createStatement();

            //Enviar la consulta y obtener resultados
            resulset = ejecutaQueryConsulta(consulta);
            int numCols = resulset.getMetaData().getColumnCount();
            Vector tuplas = new java.util.Vector();
            int cont = 0;
            //recorro la consulta y la agrego al vector

            while (resulset.next()) {
                cont++;
                java.util.Vector aux = new java.util.Vector();
                for (int i = 1; i <= numCols; i++) {
                    aux.add(resulset.getString(i));
                }
                tuplas.add(aux);
                bandera = true;
            }
            if(bandera==true){
                result=tuplas;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }catch (java.lang.ArrayIndexOutOfBoundsException e1) {}
        return result;

    }

    /* metodo para realizar consultas a la base de datos y mer retoena un vector con todos los datos */
    public ResultSet ejecutaQueryConsulta(String query) {        
        try {
            stmt = getConexion().createStatement();
            rs = stmt.executeQuery(query);
            return rs;
        } catch (SQLException ex) {
        }
        return rs;
    }
    
     /* metodo para realizar consultas a la base de datos y mer retoena un vector con todos los datos */
    public int ejecutaQueryActualiza(String query) {        
        try {
            stmt = getConexion().createStatement();
            upt = stmt.executeUpdate(query);
            return upt;
        } catch (SQLException ex) {
        }
        return upt;
    }

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

     
}

