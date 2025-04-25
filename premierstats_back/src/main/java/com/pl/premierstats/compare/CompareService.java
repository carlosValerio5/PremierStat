package com.pl.premierstats.compare;

import com.pl.premierstats.player.Player;
import com.pl.premierstats.player.PlayerCompareDTO;
import com.pl.premierstats.player.PlayerCompareMapper;
import com.pl.premierstats.player.PlayerRepository;
import com.pl.premierstats.team.Team;
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

    @Autowired
    public CompareService(PlayerRepository playerRepository, TeamRepository teamRepository) {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
    }

    public List<PlayerCompareDTO> comparePlayers(List<String> playerNames){

        //list of comparable players
        List<PlayerCompareDTO> compareDTOs = new ArrayList<>();

        //instance of mapper to convert from player to playerCompare
        PlayerCompareMapper playerCompareMapper = new PlayerCompareMapper();


        for (String name : names) {
            Optional<Player> foundPlayer = playerRepository.findByName(name);

            foundPlayer.ifPresent(player -> compareDTOs.add(playerCompareMapper.apply(player)));

        }

        return compareDTOs;
    }

    public List<TeamCompareDTO> compareTeams(List<String> teamNames){

    }
}
