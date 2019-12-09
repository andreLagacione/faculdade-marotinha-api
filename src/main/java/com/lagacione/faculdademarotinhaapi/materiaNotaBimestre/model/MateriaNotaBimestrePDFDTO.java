package com.lagacione.faculdademarotinhaapi.materiaNotaBimestre.model;

public class MateriaNotaBimestrePDFDTO {

    private Integer id;
    private String nomeMateria;
    private String notaBimestre1;
    private String notaBimestre2;
    private String notaBimestre3;
    private String notaBimestre4;
    private Integer idBoletim;
    private String mediaFinal = "N/A";

    public MateriaNotaBimestrePDFDTO() {}

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

    public Integer getIdBoletim() {
        return idBoletim;
    }

    public void setIdBoletim(Integer idBoletim) {
        this.idBoletim = idBoletim;
    }

    public String getMediaFinal() {
        return mediaFinal;
    }

    public void setMediaFinal(String mediaFinal) {
        this.mediaFinal = mediaFinal;
    }

    public static MateriaNotaBimestrePDFDTO of(MateriaNotaBimestreDTO materiaNotaBimestreDTO) {
        MateriaNotaBimestrePDFDTO materiaNotaBimestrePDFDTO = new MateriaNotaBimestrePDFDTO();
        materiaNotaBimestrePDFDTO.setId(materiaNotaBimestreDTO.getId());
        materiaNotaBimestrePDFDTO.setNomeMateria(materiaNotaBimestreDTO.getMateria().getName());
        materiaNotaBimestrePDFDTO.setNotaBimestre1(convertNota(materiaNotaBimestreDTO.getNotaBimestre1()));
        materiaNotaBimestrePDFDTO.setNotaBimestre2(convertNota(materiaNotaBimestreDTO.getNotaBimestre2()));
        materiaNotaBimestrePDFDTO.setNotaBimestre3(convertNota(materiaNotaBimestreDTO.getNotaBimestre3()));
        materiaNotaBimestrePDFDTO.setNotaBimestre4(convertNota(materiaNotaBimestreDTO.getNotaBimestre4()));
        materiaNotaBimestrePDFDTO.setIdBoletim(materiaNotaBimestreDTO.getIdBoletim());
        materiaNotaBimestrePDFDTO.setMediaFinal(materiaNotaBimestreDTO.getMediaFinal());
        return materiaNotaBimestrePDFDTO;
    }

    private static String convertNota(Double nota) {
        if (nota == null) {
            return "N/A";
        }

        return String.format("%.2f", nota);
    }

}