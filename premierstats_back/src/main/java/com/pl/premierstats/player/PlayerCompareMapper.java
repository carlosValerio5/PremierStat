package com.pl.premierstats.player;

import java.util.function.Function;

/**
 * Mapper que actúa como una función para convertir de Player a PlayerCompareDTO
 * <br/>
 * Implementa la interfaz Function la cual cuenta con el método apply que acepta un
 * parámetro y produce un resultado.
 */
public class PlayerCompareMapper implements Function<Player, PlayerCompareDTO> {
    /**
     *
     * Convierte una instancia de la clase <code>Player</code> a una de la clase <code>PlayerCompareDTO</code>.
     * <br/>
     * Toma como parámetro una instancia de la clase Player y realiza todos los cálculos para
     * popular los campos de la clase PlayerCompareDTO y así poder mostrar datos útiles para comparar dos jugadores.
     *
     * @param player Instancia de la clase Player
     * @return <code>PlayerCompareDTO</code>
     */
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
