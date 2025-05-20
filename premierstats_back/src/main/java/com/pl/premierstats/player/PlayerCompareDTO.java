package com.pl.premierstats.player;

/**
 * Data Transfer Object para el endpoint de comparación.
 * <br/>
 * Este patron de diseño es útil para mostrar datos diferentes en la misma respuesta, nos permite proporcionar
 * diferentes vistas tomando como base los mismos datos.
 *
 */
public record PlayerCompareDTO(

        //Player Information
        String playerName,
        String teamName,
        String nation,
        String position,
        Integer age,

        //Player statistics
        Integer matchesPlayed,
        Double xg, //expected goals
        Double npxg, //non-penalty expected goals
        Integer gls,
        Integer ast,
        Double xag, //expected assist goals

        //Stats per 90 minutes
        Double glsPerNinety,
        Double astPerNinety,
        Double xagPerNinety,
        Double xgPerNinety,
        Double npxgPerNinety,

        //Progression
        Integer progressiveCarries, //Progression carries
        Integer progressivePasses, //Progression passes

        //Progression per 90 minutes
        Double progressiveCarriesPerNinety,
        Double progressivePassesPerNinety,

        //Efficiency stats
        Double goalEfficiency,
        Double assistEfficiency

        // Individual player score to measure impact on the field
        //Double impact don't know how to calculate this yet
) {}
