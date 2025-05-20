package com.pl.premierstats.compare;

import com.pl.premierstats.player.Player;
import com.pl.premierstats.player.PlayerCompareDTO;
import com.pl.premierstats.player.PlayerCompareMapper;
import com.pl.premierstats.player.PlayerRepository;
import com.pl.premierstats.team.Team;
import com.pl.premierstats.team.TeamCompareDTO;
import com.pl.premierstats.team.TeamCompareMapper;
import com.pl.premierstats.team.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CompareService {

    private PlayerRepository playerRepository;
    private TeamRepository teamRepository;

    /**
     * Constructor para la clase <code>CompareService</code>, inyecta <code>playerRepository</code> y
     * <code>teamRepository</code>.
     *
     * @param playerRepository
     * @param teamRepository
     */
    @Autowired
    public CompareService(PlayerRepository playerRepository, TeamRepository teamRepository) {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
    }

    /**
     * Busca jugadores y los transforma a la clase tipo PlayerCompareDTO.
     * <br/>
     * Esta funcion recibe nombres de jugadores en forma de una lista e intenta recuperarlos
     * de la base de datos. Si se encuentra un match para el jugador este será "mapeado", a
     * la clase PlayerCompareDTO para solo regresar los datos necesarios para la comparación.
     *
     * @param playerNames
     * @return <code>List&ltPlayerCompareDTO&gt</code>
     * @throws PlayerNotFoundException Si los nombres proporcionados no coinciden con ningun jugador.
     */
    public List<PlayerCompareDTO> comparePlayers(List<String> playerNames) throws PlayerNotFoundException{

        //list of comparable players
        List<PlayerCompareDTO> compareDTOs = new ArrayList<>();

        //instance of mapper to convert from player to playerCompare
        PlayerCompareMapper playerCompareMapper = new PlayerCompareMapper();


        for (String name : playerNames) {
            Optional<Player> foundPlayer = playerRepository.findTop1ByNameContainingIgnoreCase(name);

            foundPlayer.ifPresent(player -> compareDTOs.add(playerCompareMapper.apply(player)));

        }

        if(compareDTOs.isEmpty() || compareDTOs.size() == 1) {
            throw new PlayerNotFoundException("There are no players with this name.");
        }

        return compareDTOs;
    }

    /**
     * Compara equipos y los mapea a la clase <code>TeamCompareDTO</code>.
     * <br/>
     *
     * Busca a equipos que coincidan con los nombres proporcionados a la función.
     * Si se encuentran coincidencias los objetos de la Clase <code>Team</code> se
     * transforman a instancias de la clase <code>TeamCompareDTO</code>.
     * @param teamNames
     * @return <code>List&ltTeamCompareDTO&gt</code>
     * @throws TeamNotFoundException Si no se encuentran coincidencias para los nombres proporcionados.
     */
    public List<TeamCompareDTO> compareTeams(List<String> teamNames) throws TeamNotFoundException{

        //List of comparable teams
        List<TeamCompareDTO> compareDTOs = new ArrayList<>();

        TeamCompareMapper teamCompareMapper = new TeamCompareMapper();

        for (String name : teamNames) {
            Optional<Team> foundTeam = teamRepository.findTop1ByNameIgnoreCaseContaining(name);

            foundTeam.ifPresent(team -> compareDTOs.add(teamCompareMapper.apply(team)));
        }

        if(compareDTOs.isEmpty() || compareDTOs.size() == 1) {
            throw new TeamNotFoundException("There are no matching teams with this name");
        }

        return compareDTOs;
    }
}
