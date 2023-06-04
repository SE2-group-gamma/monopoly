package com.example.monopoly.gamelogic.properties;

public class IllegalFieldException extends RuntimeException{
    public IllegalFieldException() {
        super();
    }

    public IllegalFieldException(String message) {
        super(message);
    }
}
