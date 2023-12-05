package com.questionpro.groceryapp.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.questionpro.groceryapp.constant.ResponseStatus;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Meta implements Serializable {
    @JsonProperty("code")
    private String code;
    @JsonProperty("status")
    private ResponseStatus status;
    @JsonProperty("errors")
    private List<Object> errors;
    @JsonProperty("message")
    private String message;
}