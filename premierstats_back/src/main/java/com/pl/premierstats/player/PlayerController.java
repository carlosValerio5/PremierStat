package com.pl.premierstats.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins="http://localhost:5173")
@RestController
@RequestMapping(path="api/v1/player")
public class PlayerController {
    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/top-scorers")
    public List<Player> getTopScorers() {
        return playerService.getTopScorers();
    }

    @GetMapping("/top-playmakers")
    public List<Player> getTopPlaymakers() {
        return playerService.getTopPlayMakers();
    }

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

    @PostMapping
    public ResponseEntity<Player> addPlayer(@RequestBody Player player) {
        Player createdPlayer = playerService.addPlayer(player);
        return new ResponseEntity<>(createdPlayer, HttpStatus.CREATED);
    }

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

    @DeleteMapping("/{playerName}")
    public ResponseEntity<String> deletePlayer(@PathVariable String playerName) {
        playerService.removePlayer(playerName);
        return new ResponseEntity<>("Player deleted successfully", HttpStatus.OK);
    }
}


/**
 * I want to add a comparison endpoint
 *
 * Questions:
 * How should the data be formated or structured so the comparison can be done
 * should i create another class to expose the new endpoint?
 * should i add a new table to store the comparison data?
 */