package com.tma.demo.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Getter
@Setter
public class ExceptionResponseDetail {
    private Date timestamp;
    private int status;
    private HttpStatus error;
    private String message;

    public ExceptionResponseDetail(HttpStatus error, String message) {
        timestamp = new Date();
        this.error = error;
        this.message = message;
        this.status = error.value();
    }
}
