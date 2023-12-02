package com.questionpro.groceryapp.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@AllArgsConstructor
@Getter
@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class JPAException extends RuntimeException {
    private final String message;

}
