package com.pl.premierstats.match;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

/**
 * Controlador, maneja las peticiones para el servicio de matches.<br/>
 * Este endpoint se encuentra en la dirección <code>api/v1/match</code>
 */
@CrossOrigin(origins="http://localhost:5173")
@RestController
@RequestMapping(path="api/v1/match")
public class MatchController {

    private final MatchService matchService;

    /**
     * Constructor inyecta automáticamente la dependencia <code>matchService</code>.
     *
     * @param matchService
     */
    @Autowired
    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    /**
     * Función que retorna matches buscando en diferentes parámetros con base en una sola cadena.<br/>
     *
     * Busca coincidencias buscando parecidos entre nombre de los equipos, nombre del estadio, fecha y nombre
     * del referee.
     *
     * @param pattern
     * @return <code>ResponseEntitity&ltList&ltMatch&gt&gt</code>
     */
    @GetMapping("/all")
    public ResponseEntity<List<Match>> getAll(String pattern) {
        return new ResponseEntity<>(matchService.getMatchesByParams(pattern), HttpStatus.OK);
    }

    /**
     * Obtiene matches basado en diferentes parámetros.
     * <br/>
     * Retorna una lista con todos los partidos que coinciden con alguno de los
     * parámetros pasados en la petición.<br/>
     * Cabe recalcar que solo aplicará ciertas combinaciones de estos parámetros,
     * es decir, no combina los resultados de filtrar por todos los parámetros pasados.
     * <br/>
     * Ejemplo: Si pasamos id y teamName, solo buscará por id, en cambio, si pasamos date y teamName
     * si buscará basandose en estos dos parámetros.
     *
     * @param id
     * @param teamName
     * @param venue
     * @param date
     * @return <code>ResponseEntity&ltList&ltMatch&gt&gt</code>
     */
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
