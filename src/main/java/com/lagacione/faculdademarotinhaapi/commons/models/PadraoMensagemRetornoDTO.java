package com.lagacione.faculdademarotinhaapi.commons.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PadraoMensagemRetornoDTO {

    private HttpStatus httpStatus;
    private Integer httpStatusCode;
    private String message;
}
