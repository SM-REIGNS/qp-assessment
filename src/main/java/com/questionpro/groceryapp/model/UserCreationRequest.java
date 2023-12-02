package com.questionpro.groceryapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.questionpro.groceryapp.constant.Role;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UserCreationRequest implements Serializable {
    @JsonProperty("role")
    private Role role;
}
