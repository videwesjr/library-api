package com.project.libraryApi.dto;

import com.project.libraryApi.model.BaseModel;
import jakarta.validation.constraints.NotBlank;

public class UserDTO extends BaseModel{
    @NotBlank
    private String name;

    public UserDTO() {
    }

    public UserDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
