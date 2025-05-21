package com.pl.premierstats.match;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@Profile("!production")
@ActiveProfiles("test")
public class MatchServiceTest {

    @Autowired
    private MatchService matchService;

    @Test
    public void getMatchesTest(){
        assert !matchService.getMatches().isEmpty();
    }

    @Test
    public void getMatchByIdTest() throws MatchNotFoundException {
        assert matchService.getMatchById(1) != null;
    }

}
