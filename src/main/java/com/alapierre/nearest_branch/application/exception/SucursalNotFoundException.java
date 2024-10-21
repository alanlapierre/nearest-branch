package com.alapierre.nearest_branch.application.exception;

public class SucursalNotFoundException extends RuntimeException{
    public SucursalNotFoundException(String sucursalNoEncontrada) {
        super(sucursalNoEncontrada);
    }
}
