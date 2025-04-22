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

        //90 minutes played
        Double ninetys = player.getNinetys();

        //Expected goals
        Double xg = player.getXg();

        //Non-penalty expected goals
        Double npxg = player.getNpxg();

        //Expected assisted goals
        Double xag = player.getXag();

        double glsEfficiency;
        double assistEfficiency;

        try{
            glsEfficiency = xg/(xg+npxg);
        }catch (ArithmeticException e){
            glsEfficiency = 0.0;
        }

        try{
            assistEfficiency = xag/player.getAst();
        }catch (ArithmeticException e){
            assistEfficiency = 0.0;
        }

        if(ninetys == null || ninetys == 0.0){
            return new PlayerCompareDTO(
                    player.getName(),
                    player.getTeam(),
                    player.getNation(),
                    player.getPos(),
                    player.getAge(),
                    player.getMP(),
                    player.getXg(),
                    player.getNpxg(),
                    player.getGls(),
                    player.getAst(),
                    player.getXag(),
                    0.0,
                    0.0,
                    0.0,
                    0.0,
                    0.0,
                    player.getPrgc(),
                    player.getPrgp(),
                    0.0,
                    0.0,
                    glsEfficiency,
                    assistEfficiency
            );
        }

        return new PlayerCompareDTO(
                player.getName(),
                player.getTeam(),
                player.getNation(),
                player.getPos(),
                player.getAge(),
                player.getMP(),
                player.getXg(),
                player.getNpxg(),
                player.getGls(),
                player.getAst(),
                player.getXag(),
                player.getGls()/ninetys,
                player.getAst()/ninetys,
                player.getXag()/ninetys,
                player.getXg()/ninetys,
                player.getNpxg()/ninetys,
                player.getPrgc(),
                player.getPrgp(),
                player.getPrgc()/ninetys,
                player.getPrgp()/ninetys,
                glsEfficiency,
                assistEfficiency
        );
    }
}
