package com.coursuasz.l32i.daos.microserviceutilisateur.exception;

public class ResourceAlreadyExistException extends RuntimeException {
    public ResourceAlreadyExistException(String message) {
        super(message);
    }
}