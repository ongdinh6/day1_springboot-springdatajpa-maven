package com.tma.demo.exceptions;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ErrorResponse {
    private int statusCode;
    private String statusString;
    private String message;
}
