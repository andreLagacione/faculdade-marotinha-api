package com.lagacione.faculdademarotinhaapi.dto;

import org.springframework.http.HttpStatus;

public class PadraoMensagemRetorno {

    private HttpStatus httpStatus;
    private Integer httpStatusCode;
    private String mensagem;

    public PadraoMensagemRetorno() {}

    public PadraoMensagemRetorno(HttpStatus httpStatus, Integer httpStatusCode, String mensagem) {
        this.httpStatus = httpStatus;
        this.httpStatusCode = httpStatusCode;
        this.mensagem = mensagem;
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

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
