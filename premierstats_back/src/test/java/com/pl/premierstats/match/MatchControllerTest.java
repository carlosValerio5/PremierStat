package com.pl.premierstats.match;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Profile("!production")
@ActiveProfiles("test")
public class MatchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() throws Exception {}

    @Test
    public void getAllMatchTest() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/v1/match/all")
                .param("pattern", "Arsenal"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].home").value("Arsenal"))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println(content);
    }

    @Test
    public void getMatchTest() throws Exception {
        mockMvc.perform(get("/api/v1/match"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].home").value("Burnley"));

        mockMvc.perform(get("/api/v1/match")
                .param("id", String.valueOf(0)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].home").value("Burnley"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].wk").value("1"));
        mockMvc.perform(get("/api/v1/match")
        .param("teamName", "Wolves")
        .param("date", "2023-08-19"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].wk").value("2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].away").value("Brighton"));
    }

}
