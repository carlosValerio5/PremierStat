package com.pl.premierstats.match;

/**
 * Exception used to handle cases when a match is searched and is not found
 * should be handled in MatchController so it returns a Http status.NotFound or 404
 */
public class MatchNotFoundException extends Exception {
    public MatchNotFoundException(String message) {
        super(message);
    }
}
