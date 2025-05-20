package com.pl.premierstats.teamtest;

import com.pl.premierstats.team.Team;
import com.pl.premierstats.team.TeamRepository;
import jakarta.transaction.Transactional;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TeamControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TeamRepository teamRepository;


    @Test
    public void getTeamsTest() throws Exception {
        mockMvc.perform(get("/api/v1/team")
                .param("name", "Arsenal"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Arsenal")));

        mockMvc.perform(get("/api/v1/team"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Manchester-City")));
    }

    @Test
    public void addTeamTest() throws Exception {
        mockMvc.perform(post("/api/v1/team")
                .contentType(MediaType.APPLICATION_JSON)
        .content("{\"name\": \"Prueba\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        //limpieza
        teamRepository.deleteByName("Prueba");
    }

    @Test
    public void updateTeamTest() throws Exception {
        teamRepository.deleteByName("Prueba");
        mockMvc.perform(put("/api/v1/team")
                .contentType(MediaType.APPLICATION_JSON)
        .content("{\"name\": \"Prueba\"}"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        teamRepository.save(new Team("Prueba"));

        mockMvc.perform(put("/api/v1/team")
                .contentType(MediaType.APPLICATION_JSON)
        .content("{\"name\": \"Prueba\", \"age\": \"30\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        mockMvc.perform(get("/api/v1/team")
        .param("name", "Prueba"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("\"age\":30.0")));

        teamRepository.deleteByName("Prueba");
    }

    @Test
    public void deleteTeamTest() throws Exception {
        mockMvc.perform(delete("/api/v1/team/Prueba"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        teamRepository.save(new Team("Prueba"));

        mockMvc.perform(delete("/api/v1/team/Prueba"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
