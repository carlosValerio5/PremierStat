package com.pl.premierstats.match;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {

    void deleteById(Integer id);

    Optional<Match> getMatchById(Integer id);

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
