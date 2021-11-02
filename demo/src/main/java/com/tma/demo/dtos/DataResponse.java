package com.tma.demo.dtos;

import lombok.*;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DataResponse<T> {
    private int statusCode;
    private String statusString;
    private String message;
    private T data;

    public DataResponse(T t, HttpStatus httpStatus, String message){
        this.data = t;
        this.statusCode = httpStatus.value();
        this.statusString = httpStatus.toString();
        this.message = message;
    }
}
