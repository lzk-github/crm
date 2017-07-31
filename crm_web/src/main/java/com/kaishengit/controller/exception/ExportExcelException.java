package com.kaishengit.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ExportExcelException extends RuntimeException {
    public ExportExcelException() {
    }

    public ExportExcelException(String message) {
        super(message);
    }

    public ExportExcelException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExportExcelException(Throwable cause) {
        super(cause);
    }

    public ExportExcelException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
