package com.pl.premierstats.match;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * When a match is not found a null type is returned
 */
@Component
public class MatchService {
    private final MatchRepository matchRepository;

    @Autowired
    public MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    public List<Match> getMatches() {
        return matchRepository.findAll();
    }

    public Match getMatchById(Integer id) throws MatchNotFoundException {
        if(matchRepository.getMatchById(id).isPresent()) {
            return matchRepository.getMatchById(id).get();
        }

        throw new MatchNotFoundException("Match with id " + id + " not found");
    }

    public List<Match> getMatchesByTeam(String team) {
        return matchRepository.findAll().stream().filter(
                match -> match.getAway().toLowerCase().contains(team.toLowerCase())
                        || match.getHome().toLowerCase().contains(team.toLowerCase())
        ).collect(Collectors.toList());
    }

    public List<Match> getMatchesByVenue(String venue) {
        return matchRepository.findAll().stream().filter(
                match -> match.getVenue().toLowerCase().contains(venue.toLowerCase())
        ).collect(Collectors.toList());
    }

    public List<Match> getMatchesByDate(LocalDate date) {
        return matchRepository.findAll().stream().filter(
                match -> match.getDate().equals(date)
        ).collect(Collectors.toList());
    }

    //Get matches by team AND date, team can match with away and home team
    public List<Match> getMatchesByDateAndTeam(LocalDate date, String team) {
        return matchRepository.findAll().stream().filter(
                match -> match.getDate().equals(date)
                        && match.getAway().toLowerCase().contains(team.toLowerCase())
                        || match.getHome().toLowerCase().contains(team.toLowerCase())
        ).collect(Collectors.toList());
    }

    public Match saveMatch(Match match) {
        return matchRepository.save(match);
    }

    public Match updateMatch(Integer id, Match match) throws MatchNotFoundException {
        Optional<Match> matchResult = matchRepository.getMatchById(id);
        if (matchResult.isPresent()) {
            Match matchToUpdate = matchResult.get();
            matchToUpdate.setScore(match.getScore());
            matchToUpdate.setAttendance(match.getAttendance());
            matchToUpdate.setVenue(match.getVenue());
            matchToUpdate.setReferee(match.getReferee());

            matchRepository.save(matchToUpdate);
            return matchToUpdate;
        }
        throw new MatchNotFoundException("Match not found");
    }

    public List<Match> getMatchesByParams(String param){
        if(param != null && !param.isEmpty()){
            return matchRepository.searchAll(param);
        }
        return matchRepository.findAll();
    }

    @Transactional
    public void removeMatch(Integer id) {
        matchRepository.deleteById(id);
    }

}
