package com.pl.premierstats.team;

/**
 * Data Transfer Object que contiene todos los datos necesarios para comparar equipos.
 * <br/>
 * Esta clase contiene tan solo datos de la eficiencia del equipo, información general
 * y estadísticas por cada 90 minutos.
 */
public record TeamCompareDTO(

        //General info
        String name,
        Float age, //average age per team

        //General stats
        Integer goals,
        Integer assists,
        Integer penaltyKicks,
        Integer cardYellow,
        Integer cardRed,
        Float expectedGoals,
        Float nonPenaltyXG,
        Float expectedAssistedGls,

        //Progression
        Long progressiveCarries,
        Long progressivePasses,

        //Stats per 90
        Double glsPerNinety,
        Double assistsPerNinety,
        Double xGPerNinety,
        Double npxgPerNinety,
        Double xagPerNinety,

        //Efficiency
        Double efficiencyGls,
        Double efficiencyAst

) {
}
