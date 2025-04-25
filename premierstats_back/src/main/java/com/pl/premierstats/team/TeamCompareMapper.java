package com.pl.premierstats.team;

import java.util.function.Function;


/**
 * Mapper acts as a functional class
 * It will only contain one method which will convert from Team to TeamCompareDTO
 */
public class TeamCompareMapper implements Function<Team, TeamCompareDTO> {

    @Override
    public TeamCompareDTO apply(Team team) {
        // complete ninety minutes played, should be 38 for teams
        Float ninetys = team.getNinetys();

        String teamName = team.getName();
        Float age = team.getAge();

        //General stats
        Integer goals = team.getGls();
        Integer assists = team.getAst();
        Integer penaltyKicks = team.getPk();
        Integer cardYellow = team.getCrdy();
        Integer cardRed = team.getCrdr();
        Float expectedGoals = team.getXg();
        Float nonPenaltyXG = team.getNpxg();
        Float expectedAssistedGls = team.getXag();

        //Progression
        Long progressiveCarries = team.getPrgc();
        Long progressivePasses = team.getPrgp();

        //Stats per 90
        Double glsPerNinety = (ninetys == 0.0) ? 0.0 : goals/ninetys;
        Double assistsPerNinety = (ninetys == 0.0) ? 0.0 : assists/ninetys;
        Double xGPerNinety = (ninetys == 0.0) ? 0.0 : expectedGoals/ninetys;
        Double npxgPerNinety = (ninetys == 0.0) ? 0.0 : nonPenaltyXG/ninetys;
        Double xagPerNinety = (ninetys == 0.0) ? 0.0 : expectedAssistedGls/ninetys;

        //Efficiency
        Double efficiencyGls = (expectedGoals == 0.0) ? 0.0 : goals/expectedGoals;
        Double efficiencyAst = (expectedAssistedGls == 0.0) ? 0.0 : assists/expectedAssistedGls;

        //Creating instance of TeamCompareDto and returning eat
        return new TeamCompareDTO(
                teamName,
                age,
                goals,
                assists,
                penaltyKicks,
                cardYellow,
                cardRed,
                expectedGoals,
                nonPenaltyXG,
                expectedAssistedGls,
                progressiveCarries,
                progressivePasses,
                glsPerNinety,
                assistsPerNinety,
                xGPerNinety,
                npxgPerNinety,
                xagPerNinety,
                efficiencyGls,
                efficiencyAst);
    }
}
