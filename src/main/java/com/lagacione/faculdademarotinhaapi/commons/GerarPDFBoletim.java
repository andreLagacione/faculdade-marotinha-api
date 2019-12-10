package com.lagacione.faculdademarotinhaapi.commons;

import com.lagacione.faculdademarotinhaapi.boletim.model.BoletimPDFDTO;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class GerarPDFBoletim {

    private String path;
    private String pathToReportPackage;

    public GerarPDFBoletim() {
        this.path = this.getClass().getClassLoader().getResource("").getPath();
        this.pathToReportPackage = this.path + "com/lagacione/faculdademarotinhaapi/jasper/";
    }

    public void gerarBoletim(BoletimPDFDTO boletimPDF, HttpServletResponse response) throws JRException, IOException {
        List<BoletimPDFDTO> dadosBoletim = new ArrayList<>();
        dadosBoletim.add(boletimPDF);

        Date date = new Date();
        long time = date.getTime();
        String idBoletim = String.valueOf(time).toString();

        JasperReport jasperReport = JasperCompileManager.compileReport(this.getPathToReportPackage() + "Boletim.jrxml");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<>(), new JRBeanCollectionDataSource(dadosBoletim));

        response.setContentType("application/x-download");
        response.addHeader("Content-disposition", "attachment; filename=" + idBoletim + ".pdf");
        OutputStream out = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint,out);
    }

    public String getPath() {
        return path;
    }

    public String getPathToReportPackage() {
        return pathToReportPackage;
    }
}
