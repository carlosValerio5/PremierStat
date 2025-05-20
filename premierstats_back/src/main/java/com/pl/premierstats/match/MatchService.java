package com.pl.premierstats.match;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Component
public class MatchService {
    private final MatchRepository matchRepository;

    /**
     * Constructor, inyecta matchRepository automaticamente.
     *
     * @param matchRepository
     */
    @Autowired
    public MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    /**
     * Retorna todos los partidos registrados en la base de datos.
     *
     * @return <code>List&ltMatch&gt</code>
     */
    public List<Match> getMatches() {
        return matchRepository.findAll();
    }

    /**
     * Busca matches por id.
     * <br/>
     * Recibe un id de tipo Integer que es utilizado para buscar una instancia de la
     * clase <code>Match</code> que tenga este número como id.
     *
     * @param id
     * @return <code>Match</code>
     * @throws MatchNotFoundException Si no se encuentra un <code>Match</code>
     */
    public Match getMatchById(Integer id) throws MatchNotFoundException {
        if(matchRepository.getMatchById(id).isPresent()) {
            return matchRepository.getMatchById(id).get();
        }

        throw new MatchNotFoundException("Match with id " + id + " not found");
    }

    /**
     * Busca matches por el nombre de equipo.
     * <br/>
     * Busca matches donde el nombre del equipo visitante o local contenga la cadena proporcionada.
     *
     * @param team
     * @return <code>&ltMatch&gt</code>
     */
    public List<Match> getMatchesByTeam(String team) {
        return matchRepository.findAll().stream().filter(
                match -> match.getAway().toLowerCase().contains(team.toLowerCase())
                        || match.getHome().toLowerCase().contains(team.toLowerCase())
        ).collect(Collectors.toList());
    }

    /**
     * Busca matches por el nombre del estadio.
     * <br/>
     * Encuentra Matches donde el nombre del estadio donde se jugo coincida con la cadena proporcionada.
     *
     * @param venue
     * @return <code>List&ltMatch&gt</code>
     */
    public List<Match> getMatchesByVenue(String venue) {
        return matchRepository.findAll().stream().filter(
                match -> match.getVenue().toLowerCase().contains(venue.toLowerCase())
        ).collect(Collectors.toList());
    }

    /**
     * Busca Matches por fecha.
     * <br/>
     * Busca partidos que se hayan jugado en la fecha que es proporcionada por el usuario.
     *
     * @param date
     * @return <code>List&ltMatch&gt</code>
     */
    public List<Match> getMatchesByDate(LocalDate date) {
        return matchRepository.findAll().stream().filter(
                match -> match.getDate().equals(date)
        ).collect(Collectors.toList());
    }

    /**
     * Busca matches por fecha y nombre de equipo.
     * <br/>
     * Encuentra las instancias de Match que coincidan con la fecha porporcionada
     * y con el nombre de equipo proporcionado. El equipo puede coincidir con el
     * equipo local o visitante.
     *
     * @param date
     * @param team
     * @return <code>List&ltMatch&gt</code>
     */
    //Get matches by team AND date, team can match with away and home team
    public List<Match> getMatchesByDateAndTeam(LocalDate date, String team) {
        return matchRepository.findAll().stream().filter(
                match -> match.getDate().equals(date)
                        && match.getAway().toLowerCase().contains(team.toLowerCase())
                        || match.getHome().toLowerCase().contains(team.toLowerCase())
        ).collect(Collectors.toList());
    }

    /**
     * Guarda una instancia de la clase Match en la base de datos.
     *
     * @param match
     * @return <code>Match</code>
     */
    public Match saveMatch(Match match) {
        return matchRepository.save(match);
    }

    /**
     * Actualiza un match y lo guarda en la base de datos.
     * <br/>
     * Busca coincidencias con el id proporcionado y de estar presente se aplican los cambios
     * que están almacenados en el objeto Match pasado como parámetro.<br/>
     * Cabe recalcar que si algun campo se pasa vació la función lo actualizará a Null dentro de la base de datos.
     * @param id
     * @param match
     * @return <code>Match</code>
     * @throws MatchNotFoundException Si el id no coincide con ningún <code>Match</code>
     */
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

    /**
     * Busca un Match usando una sola cadena.
     * <br/>
     * Esta función busca coincidencias en nombre de equipos, estadio, referee y fecha,
     * utilizando una misma cadena proporcionada por el usuario.
     *
     * @param param
     * @return <code>List&ltMatch&gt</code>
     */
    public List<Match> getMatchesByParams(String param){
        if(param != null && !param.isEmpty()){
            return matchRepository.searchAll(param);
        }
        return matchRepository.findAll();
    }

    /**
     * Borra un Match de la base de datos.
     * <br/>
     * Busca por id un Match y lo borra de la base de datos. Esta operación es
     * transaccional para asegurar la integridad de los datos.
     *
     * @param id
     */
    @Transactional
    public void removeMatch(Integer id) {
        matchRepository.deleteById(id);
    }

}
