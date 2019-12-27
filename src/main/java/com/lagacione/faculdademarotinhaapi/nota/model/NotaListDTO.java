package com.lagacione.faculdademarotinhaapi.nota.model;

public class NotaListDTO {
    private Integer id;
    private String nomeMateria;
    private String notaBimestre1;
    private String notaBimestre2;
    private String notaBimestre3;
    private String notaBimestre4;
    private String mediaFinal = "N/A";

    public NotaListDTO() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomeMateria() {
        return nomeMateria;
    }

    public void setNomeMateria(String nomeMateria) {
        this.nomeMateria = nomeMateria;
    }

    public String getNotaBimestre1() {
        return notaBimestre1;
    }

    public void setNotaBimestre1(String notaBimestre1) {
        this.notaBimestre1 = notaBimestre1;
    }

    public String getNotaBimestre2() {
        return notaBimestre2;
    }

    public void setNotaBimestre2(String notaBimestre2) {
        this.notaBimestre2 = notaBimestre2;
    }

    public String getNotaBimestre3() {
        return notaBimestre3;
    }

    public void setNotaBimestre3(String notaBimestre3) {
        this.notaBimestre3 = notaBimestre3;
    }

    public String getNotaBimestre4() {
        return notaBimestre4;
    }

    public void setNotaBimestre4(String notaBimestre4) {
        this.notaBimestre4 = notaBimestre4;
    }

    public String getMediaFinal() {
        return mediaFinal;
    }

    public void setMediaFinal(String mediaFinal) {
        this.mediaFinal = mediaFinal;
    }

}
