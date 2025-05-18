package com.pl.premierstats.compare;

import com.pl.premierstats.player.PlayerCompareDTO;
import com.pl.premierstats.team.TeamCompareDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CompareControllerTest {


    @Autowired
    private MockMvc mockMvc;


    @Test
    public void comparePlayerTest() throws Exception {

        mockMvc.perform(get("/api/v1/compare")
                .param("player1", "Saka")
                .param("player2", "Toney"))
                .andExpect(status().isOk());

        //First player should not exist
        mockMvc.perform(get("/api/v1/compare")
                .param("player1", "fasdfasf")
                .param("player2", "Ivan Toney"))
                .andExpect(status().isNotFound());
    }
    @Test
    public void compareTeamTest() throws Exception {

        mockMvc.perform(get("/api/v1/compare")
                        .param("team1", "Ars")
                        .param("team2", "Tottenham"))
                .andExpect(status().isOk());

        //First player should not exist
        mockMvc.perform(get("/api/v1/compare")
                        .param("team1", "fasdfasf")
                        .param("team2", "Arsenal"))
                .andExpect(status().isNotFound());

    }

}
