package com.lagacione.faculdademarotinhaapi.turma.enums;

public enum Periodo {
    MANHA("Manhã"),
    TARDE("Tarde"),
    NOITE("Noite");

    public String label;

    private Periodo(String label) {
        this.label = label;
    }

}
