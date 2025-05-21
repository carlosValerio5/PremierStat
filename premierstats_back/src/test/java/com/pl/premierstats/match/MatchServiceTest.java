package com.pl.premierstats.match;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.EnabledIf;

@SpringBootTest
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
