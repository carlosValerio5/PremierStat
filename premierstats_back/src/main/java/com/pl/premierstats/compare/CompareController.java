package com.pl.premierstats.compare;

import com.pl.premierstats.player.PlayerCompareDTO;
import com.pl.premierstats.team.TeamCompareDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@CrossOrigin(origins="http://localhost:5173")
@RestController
@RequestMapping(path="api/v1/compare")
public class CompareController {

    private final CompareService compareService;

    /**
     * @param compareService
     *<br/>
     * Constructor para la clase CompareController
     * Este constructor inyecta automaticamente la dependencia
     * <b>CompareService</b>.
     */
    @Autowired
    public CompareController(CompareService compareService) {
        this.compareService = compareService;
    }


    /**
     *
     *
     * <p>
     * Endpoint para comparar dos jugadores, los parametros recibidos representan
     * los dos nombres de los jugadores a comaprar. Estos parámetros encontrarán el
     * nombre que sea igual o similar a la cadena pasada. Es decir, podemos ingresar
     * un nombre incompleto y la función encontrará un match similar.
     * </p>
     *
     * @param player1
     * @param player2
     * @return <code>ResponseEntity&ltList&ltPlayerCompareDTO&gt&gt</code>
     *
     */
    @RequestMapping(method= RequestMethod.GET ,params={"player1", "player2"})
    public ResponseEntity<List<PlayerCompareDTO>> comparePlayer(@RequestParam String player1, @RequestParam String player2) {
        List<PlayerCompareDTO> comparablePlayers;

        try{
            comparablePlayers = compareService.comparePlayers(Arrays.asList(player1, player2));
        }catch (PlayerNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(comparablePlayers, HttpStatus.OK);
    }

    /**
     *
     * Endpoint para comparar equipos.
     * Usa el metodo GET y recibe dos nombres de dos equipos.
     * Si el nombre no coincide completamente con un nombre de equipo en la base de datos, la
     * función intentará encontrar el match más parecido que contenga la cadena proporcionada.
     *
     * @param team1
     * @param team2
     * @return
     */
    @RequestMapping(method=RequestMethod.GET, params={"team1", "team2"})
    public ResponseEntity<List<TeamCompareDTO>> compareTeam(@RequestParam String team1, @RequestParam String team2){
        List<TeamCompareDTO> comparableTeams;

        try{
            comparableTeams = compareService.compareTeams(Arrays.asList(team1, team2));
        }catch (TeamNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(comparableTeams, HttpStatus.OK);
    }

}
