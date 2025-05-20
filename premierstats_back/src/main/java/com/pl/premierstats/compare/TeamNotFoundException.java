package com.pl.premierstats.compare;

/**
 * Excepción usada para indicar que un equipo no se encontró en la capa de servicio de comparar.
 */
public class TeamNotFoundException extends Exception {

    public TeamNotFoundException(String message) {
        super(message);
    }
}
