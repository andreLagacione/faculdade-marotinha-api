package com.lagacione.faculdademarotinhaapi.dto;

import org.springframework.http.HttpStatus;

public class RetornoGerarBoletimDTO extends PadraoMensagemRetornoDTO {
    private String id;

    public RetornoGerarBoletimDTO(HttpStatus httpStatus, Integer httpStatusCode, String mensagem, String id) {
        super(httpStatus, httpStatusCode, mensagem);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
