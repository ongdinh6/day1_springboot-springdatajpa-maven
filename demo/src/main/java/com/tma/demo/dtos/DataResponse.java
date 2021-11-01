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
    private T object;
    private int statusCode;
    private String statusString;
    private String message;

    public DataResponse(T t, HttpStatus httpStatus, String message){
        this.object = t;
        this.statusCode = httpStatus.value();
        this.statusString = httpStatus.toString();
        this.message = message;
    }
}
