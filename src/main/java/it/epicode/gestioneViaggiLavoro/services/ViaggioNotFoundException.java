package it.epicode.gestioneViaggiLavoro.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ViaggioNotFoundException extends RuntimeException {
    public ViaggioNotFoundException(String message) {
        super(message);
    }
}
