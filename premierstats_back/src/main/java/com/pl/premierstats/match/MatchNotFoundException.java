package com.pl.premierstats.match;


/**
 * Excepción lanzada cuando ninguna coincidencia es encontrada para una instancia de la clase <code>Match</code>.
 */
public class MatchNotFoundException extends Exception {
    public MatchNotFoundException(String message) {
        super(message);
    }
}
