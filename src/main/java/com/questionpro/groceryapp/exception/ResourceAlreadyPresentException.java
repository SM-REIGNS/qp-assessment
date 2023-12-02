package com.questionpro.groceryapp.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@AllArgsConstructor
@Getter
@ResponseStatus(code = HttpStatus.CONFLICT)
public class ResourceAlreadyPresentException extends RuntimeException {
    private final String message;
}