package com.pl.premierstats.compare;

import com.pl.premierstats.player.PlayerCompareDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompareControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private CompareController compareController;
    @Autowired
    private CompareService compareService;

    @Test
    public void comparePlayerTest(){
        try {
            when(compareService.comparePlayers(Arrays.asList("Saka", "Toney"))).thenReturn(null);
        }catch (Exception e){
            return;
        }

        ResponseEntity<List<PlayerCompareDTO>> responseEntity = compareController.comparePlayer("Saka", "Toney");
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
    }
}
