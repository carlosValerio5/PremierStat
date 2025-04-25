package com.pl.premierstats.compare;

//Exception used to indicate no players where found in the compare Service Layer
public class PlayerNotFoundException extends Exception {

    public PlayerNotFoundException(String message) {
        super(message);
    }
}
