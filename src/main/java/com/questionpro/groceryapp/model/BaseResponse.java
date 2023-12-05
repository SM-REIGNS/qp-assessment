package com.questionpro.groceryapp.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse<T> {
    @JsonProperty("meta")
    private Meta meta;
    @JsonProperty("data")
    private T data;
    public BaseResponse(Meta meta) {
        this.meta = meta;
    }

}

