package com.pl.premierstats.match;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(path="api/v1/match")
public class MatchController {

    private final MatchService matchService;

    @Autowired
    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping
    public ResponseEntity<List<Match>> getMatches(
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String teamName,
            @RequestParam(required = false) String venue,
            @RequestParam(required = false) LocalDate date
    ) {
        if(id != null) {
            try {
                return new ResponseEntity<>(
                        Collections.singletonList(matchService.getMatchById(id)),
                        HttpStatus.OK);
            }catch (MatchNotFoundException e){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }

        else if(teamName != null && date != null) {
            return new ResponseEntity<>(matchService.getMatchesByDateAndTeam(date, teamName), HttpStatus.OK);
        }
        else if(teamName != null){
            return new ResponseEntity<>(matchService.getMatchesByTeam(teamName), HttpStatus.OK);
        }
        else if(date != null){
            return new ResponseEntity<>(matchService.getMatchesByDate(date), HttpStatus.OK);
        }
        else if (venue != null) {
            return new ResponseEntity<>(matchService.getMatchesByVenue(venue), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(matchService.getMatches(), HttpStatus.OK);
        }
    }

}
