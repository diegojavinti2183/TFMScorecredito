/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cooperativa.coopintranet.controladores.util;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.unzip.UnzipUtil;
import net.lingala.zip4j.util.Zip4jConstants;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 *
 * @author Diego
 */
public class JsfArchivo {

    public static final String FILE_SEPARATOR = System.getProperty("file.separator");

    public JsfArchivo() {
    }

    public static void writeDocument(String file, Document doc_xml) throws Exception {
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter salida = new PrintWriter(bw);
        String srt_xml = convertXML(doc_xml);

        salida.println(srt_xml);
        salida.close();
    }

    public static String convertXML(Document doc_xml) throws Exception {
        //*Formato para pasar a cadena*/
        Format format = Format.getPrettyFormat();
        //*XMLOutputter el que lo convierte a cadena*/
        XMLOutputter xmloutputter = new XMLOutputter(format);
        //*al fin lo convertimos a cadena*/
        String stringXML = xmloutputter.outputString(doc_xml);
        //*retornamos la cadena*/
        return stringXML;
    }

    public boolean validateXMLXSD(Document doc_xml, URL xsd_dir) throws Exception {
        String ruta = null;
        if (System.getProperty("os.name").toUpperCase().contains("WINDOWS")) {
            ruta = "C:\\Windows\\Temp\\";
        } else {
            ruta = "//tmp//";
        }
        try {
            Source schemaFile = new StreamSource(xsd_dir.getFile());
            //*Creamos nuestra fabrica de schemas y le decimos que seran noNamespaceSchemaLocation*/
            //*SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)*/            
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            //*creamos el Schema indicando con base en un lava.io.File */
            Schema schema = schemaFactory.newSchema(schemaFile);
            //*y de este schema obtenemos un validador*/
            Validator validator = schema.newValidator();
            //*creamos una copia temporal de nuestro xml*/
            writeDocument(ruta + "xml_tester_temp_.xml", doc_xml);
            //*y validamos*/
            final List exceptions = new LinkedList();
            try {
                validator.validate(new StreamSource(new File(ruta + "xml_tester_temp_.xml")));
            } catch (SAXException e) {
                System.out.println(exceptions.add("Reason: " + e.getLocalizedMessage()));
            }

            /* validator.setErrorHandler(new ErrorHandler() {
                @Override
                public void warning(SAXParseException exception) throws SAXException {
                     System.out.println(exception);
                    exceptions.add(exception);
                }

                @Override
                public void error(SAXParseException exception) throws SAXException {
                     System.out.println(exception);
                    exceptions.add(exception);
                }

                @Override
                public void fatalError(SAXParseException exception) throws SAXException {
                     System.out.println(exception);
                    exceptions.add(exception);
                }
            }); //Validación del XML 
             */
            if (exceptions.isEmpty()) {
                System.out.println("Archivo es valido");
                JsfUtil.addSuccessMessage("Archivo es valido");
                return true;
            } else {
                System.out.println("Archivo es invalido");
                System.out.println("NUMBER OF ERRORS: " + exceptions.size());
                for (int i = 0; i < exceptions.size(); i++) {
                    i = i + 1;
                    System.out.println("Error # " + i + ":");
                    i = i - 1;
                    System.out.println(" - Line: " + exceptions.get(i));//.getLineNumber());
                    // System.out.println(" - Column: " + exceptions.get(i).getColumnNumber());
                    //System.out.println(" - Error message: " + exceptions.get(i).getLocalizedMessage());
                    System.out.println("------------------------------");
                }
                JsfUtil.addErrorMessages(exceptions);
                return false;
            }
        } catch (JDOMException e) {
            JsfUtil.addErrorMessage(e.getMessage());
            return false;
        } catch (IOException e) {
            JsfUtil.addErrorMessage(e.getMessage());
            return false;
        }
    }

    public void archivoRotef(Document documento, String codigo, Date fecha) throws IOException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.addHeader("Content-disposition", "attachment; filename=Rotef" + codigo + JsfUtil.formatoFechaArchivo(fecha) + ".xml");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        XMLOutputter xmlOutput = new XMLOutputter();
        xmlOutput.setFormat(Format.getPrettyFormat());
        xmlOutput.output(documento, servletOutputStream);

        FacesContext.getCurrentInstance().responseComplete();
    }

    public void archivoCabeceraResu(Document documento, String codigo, Date fecha) throws IOException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.addHeader("Content-disposition", "attachment; filename=CABECERA" + codigo + JsfUtil.formatoFechaArchivo(fecha) + ".xml");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        XMLOutputter xmlOutput = new XMLOutputter();
        xmlOutput.setFormat(Format.getPrettyFormat());
        xmlOutput.output(documento, servletOutputStream);

        FacesContext.getCurrentInstance().responseComplete();
    }

    public void archivoDetalleResu(Document documento, String codigo, Date fecha) throws IOException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.addHeader("Content-disposition", "attachment; filename=DETALLE" + codigo + JsfUtil.formatoFechaArchivo(fecha) + ".xml");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        XMLOutputter xmlOutput = new XMLOutputter();
        xmlOutput.setFormat(Format.getPrettyFormat());
        xmlOutput.output(documento, servletOutputStream);

        FacesContext.getCurrentInstance().responseComplete();
    }

    public void exportarExcel(HSSFWorkbook workbook, Date fecha) throws IOException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.setContentType("application/vnd.ms-excel");
        httpServletResponse.addHeader("Content-disposition", "attachment; filename=Debitos_" + (fecha.getMonth() + 1) + "-" + (fecha.getYear() + 1900) + ".xls");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        workbook.write(servletOutputStream);
        servletOutputStream.flush();

        FacesContext.getCurrentInstance().responseComplete();
    }

    public void exportarZipSPI1(String archivos[]) throws IOException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.addHeader("Content-disposition", "attachment; filename=SPI1.zip");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();

        if (archivos != null && archivos.length > 0) {
            byte[] zip = zipFiles(archivos);
            servletOutputStream.write(zip);
            servletOutputStream.flush();
        }
        FacesContext.getCurrentInstance().responseComplete();
    }

    public void exportarBCE(String nombre, String archivos[]) throws IOException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.addHeader("Content-disposition", "attachment; filename=" + nombre + ".zip");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();

        if (archivos != null && archivos.length > 0) {
            byte[] zip = zipFiles(archivos);
            servletOutputStream.write(zip);
            servletOutputStream.flush();
        }
        FacesContext.getCurrentInstance().responseComplete();
    }

    public void exportarRESU(String archivos[], String institucion, Date fecha) throws IOException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.addHeader("Content-disposition", "attachment; filename=RESU" + institucion + JsfUtil.formatoFechaArchivo(fecha) + ".zip");
        System.out.println(institucion + JsfUtil.formatoFechaArchivo(fecha) + ".zip");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();

        if (archivos != null && archivos.length > 0) {
            byte[] zip = zipFiles(archivos);
            servletOutputStream.write(zip);
            servletOutputStream.flush();
        }
        FacesContext.getCurrentInstance().responseComplete();
    }

    public void exportarZipCarteraSEPS(String archivos[], String cartera, Date fechacorte) throws IOException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.addHeader("Content-disposition", "attachment; filename=" + cartera + "_0190325180001_" + JsfUtil.formatoFechaDDMMYYYY(fechacorte) + ".zip");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();

        if (archivos != null && archivos.length > 0) {
            byte[] zip = zipFiles(archivos);
            servletOutputStream.write(zip);
            servletOutputStream.flush();
        }
        FacesContext.getCurrentInstance().responseComplete();
    }

    private byte[] zipFiles(String[] files) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);
        byte bytes[] = new byte[2048];

        for (String fileName : files) {
            FileInputStream fis = null;
            if (System.getProperty("os.name").toUpperCase().contains("WINDOWS")) {
                fis = new FileInputStream("C:\\Windows\\Temp\\" + fileName);
            } else {
                fis = new FileInputStream("//tmp//" + fileName);
            }
            BufferedInputStream bis = new BufferedInputStream(fis);

            zos.putNextEntry(new ZipEntry(fileName));

            int bytesRead;
            while ((bytesRead = bis.read(bytes)) != -1) {
                zos.write(bytes, 0, bytesRead);
            }
            zos.closeEntry();
            bis.close();
            fis.close();
        }
        zos.flush();
        baos.flush();
        zos.close();
        baos.close();

        return baos.toByteArray();
    }

    public void unzipFiles(String nombre) throws IOException {
        String pathFile = null;
        if (System.getProperty("os.name").toUpperCase().contains("WINDOWS")) {
            pathFile = "C:\\Windows\\Temp\\";
        } else {
            pathFile = "//tmp//";
        }

        try {
            ZipFile zipFile = new ZipFile(pathFile + nombre);
            zipFile.extractAll(pathFile);

        } catch (ZipException ex) {
            Logger.getLogger(JsfArchivo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String unzipFilesSPI2(String nombre) throws IOException {
        String pathFile = null;
        String archivo = null;
        if (System.getProperty("os.name").toUpperCase().contains("WINDOWS")) {
            pathFile = "C:\\Windows\\Temp\\";
        } else {
            pathFile = "//tmp//";
        }

        try {
            ZipFile zipFile = new ZipFile(pathFile + nombre);
            zipFile.extractAll(pathFile);
            List<FileHeader> lista = zipFile.getFileHeaders();
            for (FileHeader archivos : lista) {
                if (archivos.getFileName().contains(".txt")) {
                    archivo = archivos.getFileName();
                }
            }
        } catch (ZipException ex) {
            Logger.getLogger(JsfArchivo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return archivo;
    }

    public void escribirMD5(File f, String cadena) {
        //Escritura
        try {
            FileWriter w = new FileWriter(f);
            BufferedWriter bw = new BufferedWriter(w);
            PrintWriter wr = new PrintWriter(bw);
            wr.write(cadena);//escribimos en el archivo
            //wr.append(cadena); //concatenamos en el archivo sin borrar lo existente

            wr.close();
            bw.close();
        } catch (IOException e) {
        };
    }

    public String toHexadecimal(byte[] digest) {
        String hash = "";
        for (byte aux : digest) {
            int b = aux & 0xff;
            if (Integer.toHexString(b).length() == 1) {
                hash += "0";
            }
            hash += Integer.toHexString(b);
        }
        return hash;
    }

    public String getMD5Checksum(File archivo) {
        byte[] textBytes = new byte[1024];
        MessageDigest md = null;
        int read = 0;
        String md5 = null;
        try {
            InputStream is = new FileInputStream(archivo);
            md = MessageDigest.getInstance("MD5");
            while ((read = is.read(textBytes)) > 0) {
                md.update(textBytes, 0, read);
            }
            is.close();
            byte[] md5sum = md.digest();
            md5 = toHexadecimal(md5sum);
        } catch (FileNotFoundException e) {
        } catch (NoSuchAlgorithmException e) {
        } catch (IOException e) {
        }
        return md5;
    }
}
