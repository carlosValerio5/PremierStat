package com.pl.premierstats.player;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, String> {
    void deleteByName(String playerName);

    //Optional is used to handle cases where the player might not be found in the database
    Optional<Player> findByName(String playerName);
}
