package com.chocolatada.inventory.exception;

public class InvalidEquipmentException extends RuntimeException {
    public InvalidEquipmentException(String message) {
        super(message);
    }
}