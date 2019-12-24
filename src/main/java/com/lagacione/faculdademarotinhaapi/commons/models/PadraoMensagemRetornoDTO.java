package com.lagacione.faculdademarotinhaapi.commons.models;

import org.springframework.http.HttpStatus;

public class PadraoMensagemRetornoDTO {

    private HttpStatus httpStatus;
    private Integer httpStatusCode;
    private String message;

    public PadraoMensagemRetornoDTO() {}

    public PadraoMensagemRetornoDTO(HttpStatus httpStatus, Integer httpStatusCode, String message) {
        this.httpStatus = httpStatus;
        this.httpStatusCode = httpStatusCode;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(Integer httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String mensagem) {
        this.message = message;
    }
}
