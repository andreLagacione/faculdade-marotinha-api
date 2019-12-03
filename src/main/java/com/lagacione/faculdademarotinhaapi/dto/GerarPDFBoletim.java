package com.lagacione.faculdademarotinhaapi.dto;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class GerarPDFBoletim {

    private String path;
    private String pathToReportPackage;

    public GerarPDFBoletim() {
        this.path = this.getClass().getClassLoader().getResource("").getPath();
//        this.pathToReportPackage = this.path + "com/lagacione/faculdademarotinhaapi/jasper/";
        this.pathToReportPackage = "C:/Jobs/estudando/faculdade-marotinha-api/src/main/java/com/lagacione/faculdademarotinhaapi/jasper/";
    }

    public String gerarBoletim(BoletimPDFDTO boletimPDF) throws JRException {
        String nomeAluno = boletimPDF.getAluno();

        List<BoletimPDFDTO> dadosBoletim = new ArrayList<>();
        dadosBoletim.add(boletimPDF);

        Date date = new Date();
        long time = date.getTime();
        String idBoletim = String.valueOf(time).toString();

        JasperReport jasperReport = JasperCompileManager.compileReport(this.getPathToReportPackage() + "Boletim.jrxml");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<>(), new JRBeanCollectionDataSource(dadosBoletim));
        JasperExportManager.exportReportToPdfFile(jasperPrint, "C:/temp/faculdade-marotinha/boletins/" + idBoletim + ".pdf");

        return idBoletim;
    }

    public String getPath() {
        return path;
    }

    public String getPathToReportPackage() {
        return pathToReportPackage;
    }
}
