package com.pl.premierstats.compare;

/**
 * Excepción usada para indicar que no se encontró un jugador en la capa de servicio para comparar.
 */
//Exception used to indicate no players where found in the compare Service Layer
public class PlayerNotFoundException extends Exception {

    public PlayerNotFoundException(String message) {
        super(message);
    }
}
