package com.pl.premierstats.player;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

@Component
public class PlayerService {
    private final PlayerRepository playerRepository;

    //Inject dependency
    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> getPlayers() {
        return playerRepository.findAll();
    }

    public List<Player> getPlayersByTeam(String team) {
        return playerRepository.findAll().stream()
                .filter(player -> team.equals(player.getTeam())).
                collect(Collectors.toList());
    }

    public List<Player> getPlayersByName(String searchText) {
        return playerRepository.findAll().stream()
                .filter(player -> player.getName().toLowerCase().contains(searchText.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Player> getPlayersByPosition(String position) {
        return playerRepository.findAll().stream()
                .filter(player->player.getPos().toLowerCase().contains(position.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Player> getPlayersByNation(String nation){
        return playerRepository.findAll().stream()
                .filter(player->player.getNation().toLowerCase().contains(nation.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Player> getPlayersByTeamAndPosition(String team, String position) {
        return playerRepository.findAll().stream()
                .filter(player -> team.equals(player.getTeam())
                        && position.equals(player.getPos()))
                .collect(Collectors.toList());
    }

    public Player addPlayer(Player player) {
        playerRepository.save(player);
        return player;
    }

    public Player updatePlayer(Player updatedPlayer) {
        Optional<Player> existingPlayer = playerRepository.findByName(updatedPlayer.getName());

        if (existingPlayer.isPresent()) {
            Player playerToUpdate = existingPlayer.get();
            playerToUpdate.setName(updatedPlayer.getName());
            playerToUpdate.setNation(updatedPlayer.getNation());
            playerToUpdate.setTeam(updatedPlayer.getTeam());
            playerToUpdate.setPos(updatedPlayer.getPos());
            playerToUpdate.setAge(updatedPlayer.getAge());


            playerRepository.save(playerToUpdate);
            return playerToUpdate;
        }

        return null;
    }

    public List<Player> getTopScorers(){
        return playerRepository.findTop5ByOrderByGlsDesc();
    }

    @Transactional
    public void removePlayer(String playerName) {
        playerRepository.deleteByName(playerName);
    }

    public List<Player> getTopPlayMakers(){
        double xAgWeight = 0.3;
        double prgpWeight = 0.4;
        double asstWeight = 0.3;

        PriorityQueue<Player> players = new PriorityQueue<>(new PlayerPlayMakerComparator());

        for(Player player : getPlayers()){

            Double playmakerScore = safe(player.getzXag()) * xAgWeight + safe(player.getzProgressivePasses()) * prgpWeight
                    + safe(player.getzAssists()) * asstWeight;

            player.setPlayMakerScore(playmakerScore);

            players.add(player);

            if(players.size() > 5){
                players.poll();
            }
        }

        return players.stream().toList();
    }

    private double safe(Double value){
        return value != null ? value : 0.0;
    }
}
