package com.pl.premierstats.team;

/**
 * The TeamCompareDTO is a register type class which will include all
 * the data needed to compare teams.
 * This class will contain only data about the teams efficiency, general info and its stats per
 * 90 minutes.
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
