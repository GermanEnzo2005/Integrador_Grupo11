package com.aydsii.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 Respuesta genérica para todos los mensajes al usuario la del tp anteriro
 */
@Data
@AllArgsConstructor
public class ApiResponse<T> {
    private int status;
    private String message;
    private T data;

    public static <T> ApiResponse<T> success(int status, String message, T data) {
        return new ApiResponse<>(status, message, data);
    }

    public static <T> ApiResponse<T> error(int status, String message) {
        return new ApiResponse<>(status, message, null);
    }
}
