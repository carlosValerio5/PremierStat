package com.pl.premierstats.player;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, String> {
    void deleteByName(String playerName);

    //Optional is used to handle cases where the player might not be found in the database
    //Note: The parameter must exactly match the players name, it will not match if name is not complete
    Optional<Player> findByName(String playerName);

    List<Player> findTop5ByOrderByGlsDesc();

    Optional<Player> findTop1ByNameContainingIgnoreCase(String name);
}
