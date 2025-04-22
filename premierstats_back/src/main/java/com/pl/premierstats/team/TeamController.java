package com.pl.premierstats.team;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/team")
public class TeamController {
    private final TeamService teamService;

    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping
    public List<Team> getTeams(
            @RequestParam(required = false) String name
    ){
        if(name != null){
            return teamService.getTeamByName(name);
        }
        return teamService.getTeams();
    }

    @PostMapping
    public ResponseEntity<Team> addTeam(@RequestBody Team team){
        Team createdTeam = teamService.addTeam(team);
        return new ResponseEntity<>(createdTeam, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Team> updateTeam(@RequestBody Team team){
        Team updatedTeam = teamService.updateTeam(team);
        if (updatedTeam == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<>(updatedTeam, HttpStatus.OK);
        }
    }

    @DeleteMapping("/{teamName}")
    public ResponseEntity<String> deleteTeam(@PathVariable String teamName){
        teamService.removeTeam(teamName);
        return new ResponseEntity<>("Team deleted successfully",HttpStatus.OK);
    }
}
