package com.pl.premierstats.compare;

import com.pl.premierstats.player.PlayerCompareDTO;
import com.pl.premierstats.team.TeamCompareDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompareControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private CompareController compareController;


    @Test
    public void comparePlayerTest() throws Exception {

        ResponseEntity<List<PlayerCompareDTO>> responseEntity = compareController.comparePlayer("Bukayo Saka", "Ivan Toney");
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        //Buk Saka should not exist
        responseEntity = compareController.comparePlayer("Buk Saka", "Ivan Toney");
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
    @Test
    public void compareTeamTest() throws Exception {

        ResponseEntity<List<TeamCompareDTO>> responseEntity = compareController.compareTeam("Brighton", "Arsenal");
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        responseEntity = compareController.compareTeam("Brighton", "Arsal");
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);


    }

}
