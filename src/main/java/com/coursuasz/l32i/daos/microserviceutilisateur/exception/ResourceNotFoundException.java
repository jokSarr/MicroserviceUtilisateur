package com.coursuasz.l32i.daos.microserviceutilisateur.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}