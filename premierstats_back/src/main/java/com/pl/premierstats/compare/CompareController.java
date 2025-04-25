package com.pl.premierstats.compare;

import com.pl.premierstats.player.PlayerCompareDTO;
import com.pl.premierstats.team.TeamCompareDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(path="api/v1/compare")
public class CompareController {

    private final CompareService compareService;

    @Autowired
    public CompareController(CompareService compareService) {
        this.compareService = compareService;
    }

    //Note: Request Params must exactly match players name
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

    @RequestMapping(method=RequestMethod.GET, params="team")
    public List<TeamCompareDTO> compareTeam(@RequestParam String team1, @RequestParam String team2){
        return null;
    }

}
