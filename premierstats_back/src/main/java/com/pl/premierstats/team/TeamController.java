package com.pl.premierstats.team;

import com.pl.premierstats.compare.TeamNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Clase controller que gestiona las operaciones CRUD de la entidad Team.
 */
@CrossOrigin(origins="http://localhost:5173")
@RestController
@RequestMapping(path = "api/v1/team")
public class TeamController {
    private final TeamService teamService;

    /**
     * Constructor que inyecta automáticamente la dependencia TeamService.
     *
     * @param teamService Instancia de <code>TeamService</code>
     */
    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    /**
     * Funcion que obtiene a los equipos filtrando por nombre.
     * <br/>
     * Si ningún nombre se proporciona simplemente retornará la lista completa de equipos.
     * De estar presente el nombre, se buscarán los equipos que contengan la
     * cadena proporcionada en sus nombres.
     *
     * @param name String
     * @return <code>List&ltTeam&gt</code>
     */
    @GetMapping
    public List<Team> getTeams(
            @RequestParam(required = false) String name
    ){
        if(name != null){
            return teamService.getTeamByName(name);
        }
        return teamService.getTeams();
    }

    /**
     * Añade un equipo a la base de datos.
     * <br/>
     *
     * Recibe un objeto de Team en el cuerpo de la petición
     * y lo añade a la base de datos.
     * Si la operación es exitosa se regresa un status CREATED.
     *
     * @param team Instancia de la clase <code>Team</code>
     * @return <code>ResponseEntity&ltTeam&gt</code>
     */
    @PostMapping
    public ResponseEntity<Team> addTeam(@RequestBody Team team){
        Team createdTeam = teamService.addTeam(team);
        return new ResponseEntity<>(createdTeam, HttpStatus.CREATED);
    }

    /**
     * Actualiza un equipo y lo guarda.
     * <br/>
     *
     * La función recibe como parámetro un objeto de tipo <code>Team</code>
     * en el cuerpo de la petición.
     * Se llama la función <code>updateTeam()</code> la cual retorna un objeto Team
     * si la operación fue exitosa. Si el objeto es nulo se regresa un status <code>404 NOT_FOUND</code>
     * <br/>
     * Si la operación fue exitosa se regresa un status <code>OK</code>
     *
     * @param team Instancia de la clase <code>Team</code>
     * @return <code>ResponseEntity&ltTeam&gt</code>
     */
    @PutMapping
    public ResponseEntity<Team> updateTeam(@RequestBody Team team){
        Team updatedTeam = teamService.updateTeam(team);
        if (updatedTeam == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<>(updatedTeam, HttpStatus.OK);
        }
    }

    /**
     * Elimina un equipo de la base de datos.
     * <br/>
     * Recibe como parámetro el nombre de un equipo. Si la operación es exitosa
     * se retorna un status OK. Si el equipo no se encontro retorna un status
     * NOT_FOUND
     *
     * @param teamName String
     * @return <code>ResponseEntity&ltString&gt</code>
     */
    @DeleteMapping("/{teamName}")
    public ResponseEntity<String> deleteTeam(@PathVariable String teamName){
        try {
            teamService.removeTeam(teamName);
        }catch (TeamNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Team deleted successfully",HttpStatus.OK);
    }
}
