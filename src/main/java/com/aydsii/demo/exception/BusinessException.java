package com.aydsii.demo.exception;

import org.springframework.http.HttpStatus;

//error que sale cunado se rompio una regla de negocio como tener stock insuficionete o querer comprar un carrito vacio
public class BusinessException extends RuntimeException {

    private final HttpStatus status;

    public BusinessException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public BusinessException(String message) {
        this(message, HttpStatus.BAD_REQUEST);
    }

    public HttpStatus getStatus() {
        return status;
    }
}
