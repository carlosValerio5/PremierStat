package com.pl.premierstats.match;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {

    void deleteById(Integer id);

    Optional<Match> getMatchById(Integer id);

}
