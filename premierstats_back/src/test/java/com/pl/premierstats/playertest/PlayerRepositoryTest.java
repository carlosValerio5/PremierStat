package com.pl.premierstats.playertest;

import com.pl.premierstats.player.Player;
import com.pl.premierstats.player.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PlayerRepositoryTest {

    @Autowired
    private PlayerRepository playerRepository;


    @Test
    public void getPlayerByNameEmptyTest(){
        assertTrue(playerRepository.findByName("player not registered").isEmpty());
    }

    @Test
    public void getPlayerByNameSinglePlayerTest(){

        // Check if the repository getbyname shares common letters or matches a player exactly
        Optional<Player> player = playerRepository.findByName("Bukayo Saka");

        assertEquals("Bukayo Saka", player.get().getName());
    }
}
