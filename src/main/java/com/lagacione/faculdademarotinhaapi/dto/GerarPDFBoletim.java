package com.lagacione.faculdademarotinhaapi.dto;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.util.List;

public class GerarPDFBoletim {

    private String path;
    private String pathToReportPackage;

    public GerarPDFBoletim() {
        this.path = this.getClass().getClassLoader().getResource("").getPath();
//        this.pathToReportPackage = this.path + "com/lagacione/faculdademarotinhaapi/jasper/";
        this.pathToReportPackage = "C:/Jobs/estudando/faculdade-marotinha-api/src/main/java/com/lagacione/faculdademarotinhaapi/jasper/";
    }

    public void imprimir(List<BoletimPDFDTO> boletimPDF) throws JRException {
        String nomeAluno = boletimPDF.get(0).getAluno();
        JasperReport report = JasperCompileManager.compileReport(this.getPathToReportPackage() + "Boletim.jrxml");
        JasperPrint print = JasperFillManager.fillReport(report, null, new JRBeanCollectionDataSource(boletimPDF));
        JasperExportManager.exportReportToPdfFile(print, "C:/Jobs/estudando/faculdade-marotinha-api/boletins/Boletim " + nomeAluno + ".pdf");
    }

    public String getPath() {
        return path;
    }

    public String getPathToReportPackage() {
        return pathToReportPackage;
    }
}
