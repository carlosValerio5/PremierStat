package com.pl.premierstats.player;

import java.util.function.Function;

/**
 * A mapper acts a function to convert from one object type to another
 *
 * In this case it converts a player object to a player compare object
 * It uses the Function class to implement the mapping
 */
public class PlayerCompareMapper implements Function<Player, PlayerCompareDTO> {
    @Override
    public PlayerCompareDTO apply(Player player) {

        Double ninetys = player.getNinetys();

        //Player info
        String playerName = player.getName();
        String teamName = player.getTeam();
        String nation = player.getNation();
        String position = player.getPos();
        Integer age = player.getAge();


        //Statistics
        Integer minutesPlayed = player.getMP();
        Double xg = player.getXg(); //expected goals
        Double npxg = player.getNpxg(); //non-penalty expected goals
        Integer gls = player.getGls();
        Integer ast = player.getAst();
        Double xag = player.getXag(); //expected assist goals

        //Stats per 90 minutes
        Double glsPerNinety = ninetys == 0.0 ? 0.0 : gls/ninetys;
        Double astPerNinety = ninetys == 0.0 ? 0.0 : ast/ninetys;
        Double xagPerNinety = ninetys == 0.0 ? 0.0 : xag/ninetys;
        Double xgPerNinety = ninetys == 0.0 ? 0.0 : xg/ninetys;
        Double npxgPerNinety = ninetys == 0.0 ? 0.0 : npxg/ninetys;

        //Progression
        Integer progressiveCarries = player.getPrgc(); //Progression carries
        Integer progressivePasses = player.getPrgp(); //Progression passes


        //Progression per 90 minutes
        Double progressiveCarriesPerNinety = ninetys == 0.0 ? 0.0 : progressiveCarries/ninetys;
        Double progressivePassesPerNinety = ninetys == 0.0 ? 0.0 : progressivePasses/ninetys;

        //Efficiency stats
        Double goalEfficiency = (xg == 0.0) ? 0.0 : gls/xg;
        Double assistEfficiency = (xag == 0.0) ? 0.0 : ast/xag;

        return new PlayerCompareDTO(
                playerName,
                teamName,
                nation,
                position,
                age,
                minutesPlayed,
                xg,
                npxg,
                gls,
                ast,
                xag,
                glsPerNinety,
                astPerNinety,
                xagPerNinety,
                xgPerNinety,
                npxgPerNinety,
                progressiveCarries,
                progressivePasses,
                progressiveCarriesPerNinety,
                progressivePassesPerNinety,
                goalEfficiency,
                assistEfficiency
        );
    }
}
