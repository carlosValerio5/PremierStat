package com.pl.premierstats.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Clase controlador para el endpoint para Player.
 * <br/>
 * Se puede alcanzar desde la dirección <code>api/v1/player</code>.
 * Maneja todas las operaciones CRUD para la entidad <code>Player</code>.
 */
@CrossOrigin(origins="http://localhost:5173")
@RestController
@RequestMapping(path="api/v1/player")
public class PlayerController {
    private final PlayerService playerService;

    /**
     * Constructor que inyecta dependencias automáticamente.
     *
     * @param playerService Instancia de la clase PlayerService, inyectado por Spring Boot
     */
    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    /**
     * Mapping para el método GET, regresa los 5 jugadores con más goles.
     * <br/>
     * Llama a la función <code>getTopScorers()</code> de la capa de servicio
     * para obtener a los 5 mejores goleadores de la liga.
     * Se puede alcanzar usando la dirección:
     * <code>api/v1/player/top-scorers</code>
     *
     * @return <code>List&ltPlayer&gt</code>
     */
    @GetMapping("/top-scorers")
    public List<Player> getTopScorers() {
        return playerService.getTopScorers();
    }

    /**
     * Función que regresa a los mejores creadores de juego de la liga.
     * <br/>
     *
     * Usando el campo <code>playMakerScore</code> de la entidad Player,
     * clasificamos a los mejores creadores de juego y mostramos a los 5
     * jugadores con el mayor valor en este campo.
     *
     * @return <code>List&ltPlayer&gt</code>
     */
    @GetMapping("/top-playmakers")
    public List<Player> getTopPlaymakers() {
        return playerService.getTopPlayMakers();
    }


    /**
     * Mapping para obtener jugadores filtrando por parámetros.
     * <br/>
     * Recibe cuatro parámetros, por los cuales se filtrará la búsqueda; sin embargo,
     * los filtros no se aplican al mismo tiempo en todas las ocasiones.
     * Estos filtros se aplican simultaneamente solo en ciertas circunstancias.
     * Por ejemplo: cuando se proporciona team y position.
     *
     * @param team String
     * @param name String
     * @param position String
     * @param nation String
     * @return <code>List&ltPlayer&gt</code>
     */
    @GetMapping
    public List<Player> getPlayers(
            @RequestParam(required = false) String team,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String position,
            @RequestParam(required = false) String nation
    ) {
        if (team != null && position != null) {
            return playerService.getPlayersByTeamAndPosition(team, position);
        }
        else if (team != null) {
            return playerService.getPlayersByTeam(team);
        }
        else if (name != null) {
            return playerService.getPlayersByName(name);
        }
        else if (nation != null) {
            return playerService.getPlayersByNation(nation);
        }
        else if (position != null) {
            return playerService.getPlayersByPosition(position);
        }
        else {
            return playerService.getPlayers();
        }
    }

    /**
     * Añade un jugador a la base de datos.
     * <br/>
     * Recibe una petición de tipo http la cual debe contener
     * un cuerpo con los datos del nuevo jugador a agregar.
     * Si el jugador se crea satisfactoriamente se retornará
     * un status <code>CREATED</code>.
     *
     * @param player
     * @return <code>ResponseEntity&ltPlayer&gt</code>
     */
    @PostMapping
    public ResponseEntity<Player> addPlayer(@RequestBody Player player) {
        Player createdPlayer = playerService.addPlayer(player);
        return new ResponseEntity<>(createdPlayer, HttpStatus.CREATED);
    }

    /**
     * Actualiza un jugador presente en la base de datos.
     * <br/>
     * Utiliza el nombre proporcionado en el cuerpo para buscar el jugador en la base de datos.
     * Recibe una petición PUT con un cuerpo que cuenta con los datos que se
     * desean actualizar. Cabe recalcar que los datos que no se proporcionen serán
     * actualizados a NULL.
     *
     * @param player Instancia de <code>Player</code>
     * @return <code>ResponseEntity&ltPlayer&gt</code>
     */
    @PutMapping
    public ResponseEntity<Player> updatePlayer(@RequestBody Player player) {
        Player updatedPlayer = playerService.updatePlayer(player);

        //Since player could be absent as of now well return not found
        if (updatedPlayer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<>(updatedPlayer, HttpStatus.OK);
        }
    }

    /**
     * Borra a un jugador de la base de datos.
     * <br/>
     * Busca a un jugador por su nombre y si se encuentra presente este se borra de la base de datos.
     *
     * @param playerName String
     * @return <code>ResponseEntity&ltString&gt</code>
     */
    @DeleteMapping("/{playerName}")
    public ResponseEntity<String> deletePlayer(@PathVariable String playerName) {
        playerService.removePlayer(playerName);
        return new ResponseEntity<>("Player deleted successfully", HttpStatus.OK);
    }
}