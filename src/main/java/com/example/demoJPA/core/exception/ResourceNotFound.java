package com.example.demoJPA.core.exception;

import lombok.Getter;

@Getter
public class ResourceNotFound extends Exception{

    private String id;

    public ResourceNotFound(String message, String id) {
        super(message);
        this.id = id;

    }

    public ResourceNotFound(String message, String id, Throwable cause) {
        super(message, cause);
        this.id = id;
    }
}
