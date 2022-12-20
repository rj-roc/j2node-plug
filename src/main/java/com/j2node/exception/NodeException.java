package com.j2node.exception;

import lombok.Data;

@Data
public class NodeException extends RuntimeException {

    private String message;

    public NodeException(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
