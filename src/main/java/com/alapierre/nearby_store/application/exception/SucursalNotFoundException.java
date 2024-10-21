package com.alapierre.nearby_store.application.exception;

public class SucursalNotFoundException extends RuntimeException{
    public SucursalNotFoundException(String sucursalNoEncontrada) {
        super(sucursalNoEncontrada);
    }
}
