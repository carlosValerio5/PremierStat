package com.pl.premierstats.team;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class TeamService {
    private final TeamRepository teamRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public List<Team> getTeams(){
        return teamRepository.findAll();
    }

    public List<Team> getTeamByName(String teamName){
        return teamRepository.findAll().stream()
                .filter(team -> team.getName().toLowerCase().contains(teamName.toLowerCase()))
                .collect(Collectors.toList());
    }

    public Team addTeam(Team team){
        teamRepository.save(team);
        return team;
    }

    public Team updateTeam(Team team){

        //Get Team from repository, using optional to handle not found case
        Optional<Team> existingTeam = teamRepository.findByName(team.getName());
        if(existingTeam.isPresent()){
            Team updatedTeam = existingTeam.get();
            updatedTeam.setName(team.getName());
            updatedTeam.setAge(team.getAge());
            updatedTeam.setGls(team.getGls());
            updatedTeam.setAst(team.getAst());
            teamRepository.save(updatedTeam);
            return updatedTeam;
        }

        return null;
    }

    @Transactional
    public void removeTeam(String teamName){
        teamRepository.deleteByName(teamName);
    }
}
