package com.pl.premierstats.match;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {

    /**
     * Remueve un Match de la base de datos buscando por Id.
     *
     * @param id
     */
    void deleteById(Integer id);

    /**
     * Busca Matches por id.
     * <br/>
     * Recibe un id y busca en la base de datos el Match correspondiente.
     * Retorna un objeto de tipo opcional el cual nos permite evitar excepciones como
     * NullPointerException.
     * @param id
     * @return <code>Optional&ltMatch&gt</code>
     */
    Optional<Match> getMatchById(Integer id);

    /**
     * Native Query para buscar matches basado en una sola string.
     * <br/>
     * Busca coincidencias en la base de datos usando un mismo patron y revisa
     * si esta string es contenida dentro del nombre de alguno de los dos equipos,
     * el nombre del estadio, nombre del referee o la fecha.
     * @param searchTerm
     * @return
     */
    @Query(""" 
SELECT m FROM Match m
WHERE LOWER(m.home) LIKE LOWER(concat('%', :q, '%'))
OR LOWER(m.away) LIKE LOWER(concat('%', :q, '%'))
OR LOWER(m.venue) LIKE LOWER(concat('%', :q, '%'))
OR LOWER(STR(m.date)) LIKE concat('%', :q, '%')
OR LOWER(m.referee) LIKE LOWER(concat('%', :q, '%'))
            """)
    List<Match> searchAll(@Param("q") String searchTerm);

}
