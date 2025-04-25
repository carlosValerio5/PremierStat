package com.pl.premierstats.playertest;

import com.pl.premierstats.player.PlayerController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlayerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private PlayerController controller;

    @Test
    public void testPlayerController() {
        assertThat(controller).isNotNull();
    }


}
