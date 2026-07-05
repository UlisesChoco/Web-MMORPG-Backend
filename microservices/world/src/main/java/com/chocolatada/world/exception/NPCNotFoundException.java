package com.chocolatada.world.exception;
public class NPCNotFoundException extends RuntimeException {
    public NPCNotFoundException(String message) {
        super(message);
    }
}