package com.pl.premierstats.team;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, String> {
    void deleteByName(String teamName);

    Optional<Team> findByName(String teamName);

    Optional<Team> findTop1ByNameIgnoreCaseContaining(String teamName);
}
