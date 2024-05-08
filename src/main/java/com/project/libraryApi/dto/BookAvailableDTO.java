package com.project.libraryApi.dto;

import com.project.libraryApi.model.BaseModel;

public class BookAvailableDTO extends BaseModel {
    Boolean available;

    public BookAvailableDTO(Boolean available) {
        this.available = available;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}
