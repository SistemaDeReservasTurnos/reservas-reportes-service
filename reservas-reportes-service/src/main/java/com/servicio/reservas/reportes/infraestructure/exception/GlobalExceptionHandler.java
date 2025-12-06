package com.servicio.reservas.reportes.infraestructure.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(
            MethodArgumentTypeMismatchException ex,
            HttpServletRequest request) {

        Object detailsError;

        if (ex.getRequiredType() != null && ex.getRequiredType().isEnum()) {
            Map<String, String> info = new HashMap<>();
            info.put("parameter", ex.getName());
            info.put("received_value:", String.valueOf(ex.getValue()));
            info.put("allowed_values", Arrays.toString(ex.getRequiredType().getEnumConstants()));
            detailsError = info;
        } else {
            detailsError = "The value '" + ex.getValue() + "'not valid for parameter '" + ex.getName() + "'";
        }

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Error in request parameters ",
                request.getRequestURI(),
                detailsError
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
