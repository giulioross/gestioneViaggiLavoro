package it.epicode.gestioneViaggiLavoro.exeptions;

public class DipendenteNotFoundException extends RuntimeException {
    public DipendenteNotFoundException(String username) {
        super("Dipendente non trovato: " + username);
    }
}