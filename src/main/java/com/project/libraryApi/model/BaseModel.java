package com.project.libraryApi.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;
import java.text.MessageFormat;

public class BaseModel implements Serializable {

    ObjectMapper mapper = new ObjectMapper();

    @Override
    public String toString() {
            final String simpleName = this.getClass().getSimpleName();
        try {
            return MessageFormat.format("{0} -> [{1}]", simpleName, mapper.writeValueAsString(this));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
