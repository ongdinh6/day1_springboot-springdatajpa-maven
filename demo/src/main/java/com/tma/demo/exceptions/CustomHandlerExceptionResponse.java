package com.tma.demo.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;

@RestControllerAdvice
public class CustomHandlerExceptionResponse extends ResponseEntityExceptionHandler {
    private static Logger log = LoggerFactory.getLogger(CustomHandlerExceptionResponse.class);

    //bad-request missing param
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        headers.set("log-detail-trace", ex.getMessage());
        return new ResponseEntity<>(new ExceptionResponseDetail(HttpStatus.BAD_REQUEST, ex.getMessage()), headers, status);
    }

    //bad-request handler exception internal
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.info("CustomHandlerExceptionResponse - ResponseEntityExceptionHandler - handlerExceptionInternal");
        headers.set("log-detail-trace", ex.getMessage());
        return new ResponseEntity<>(new ExceptionResponseDetail(HttpStatus.BAD_REQUEST, "Request invalid! Error: " + ex.getMessage()), headers, status);
    }

    //method not allow (script: none data put)
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.info("CustomHandlerExceptionResponse - ResponseEntityExceptionHandler - handlerMethodNotAllow");
        headers.set("log-detail-trace", ex.getMessage());
        return new ResponseEntity<>(new ExceptionResponseDetail(HttpStatus.METHOD_NOT_ALLOWED, "Request param is invalid! Error: " + ex.getMessage()), headers, status);
    }

    //InvalidDefinitionException
    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        headers.set("log-detail-trace", ex.getMessage());
        return super.handleBindException(ex, headers, status, request);
    }

    // my custom bad-request
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionResponseDetail>  handlerBadRequestException(BadRequestException ex, WebRequest req) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("log-detail-trace", ex.getMessage());
        return new ResponseEntity<>(new ExceptionResponseDetail(HttpStatus.BAD_REQUEST, ex.getMessage()), headers, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InternalServerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ExceptionResponseDetail> handlerInternalServerException(InternalServerException ex, WebRequest req) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("log-detail-trace", ex.getMessage());
        return new ResponseEntity<>(new ExceptionResponseDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()), headers, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ExceptionResponseDetail> handlerNotFoundException(NotFoundException ex, WebRequest req) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("log-detail-trace", ex.getMessage());
        return new ResponseEntity<>(new ExceptionResponseDetail(HttpStatus.NOT_FOUND, ex.getMessage()), headers, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        headers.set("log-detail-trace", ex.getMessage());
        return super.handleNoHandlerFoundException(ex, headers, status, request);
    }

    //Login failed
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ExceptionResponseDetail> handlerBadCredentialsException(BadCredentialsException ex, WebRequest req) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("log-detail-trace", ex.getMessage());
        return new ResponseEntity<>(new ExceptionResponseDetail(HttpStatus.UNAUTHORIZED, ex.getMessage()), headers, HttpStatus.UNAUTHORIZED);
    }

}
