package com.lagacione.faculdademarotinhaapi.commons.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
public class ActionNotAllowedException extends RuntimeException {

    public ActionNotAllowedException() {}

    public ActionNotAllowedException(String message) {
        super(message);
    }

    public ActionNotAllowedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ActionNotAllowedException(Throwable cause) {
        super(cause);
    }

}
