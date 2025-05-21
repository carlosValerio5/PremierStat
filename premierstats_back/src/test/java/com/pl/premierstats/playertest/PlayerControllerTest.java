package com.pl.premierstats.playertest;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.EnabledIf;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
public class PlayerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void borrar() throws Exception {
        mockMvc.perform(delete("/api/v1/player/prueba"));
    }

    @Test
    public void topScorersTest() throws Exception {
        mockMvc.perform(get("/api/v1/player/top-scorers"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Erling Haaland"));
    }

    @Test
    public void topPlayMakersTest() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/v1/player/top-playmakers"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Rodri")))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println(content);
    }

    @Test
    public void getPlayersTest() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/v1/player")
                .param("team", "Arsenal")
        .param("position", "MF"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Martin Ødegaard")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("MF")))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println(content);

        mockMvc.perform(get("/api/v1/player")
                        .param("team", "Arsenal"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(
                        Matchers.containsString("Martin Ødegaard")));

        mockMvc.perform(get("/api/v1/player")
        .param("position", "FW"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(
                        Matchers.containsString("Erling Haaland")));

        mockMvc.perform(get("/api/v1/player")
        .param("name", "Saka"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Bukayo Saka")));

        mockMvc.perform(get("/api/v1/player")
                .param("nation", "MEX"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Edson Álvarez")));
    }

    @Test
    public void agregarPlayerTest() throws Exception {
        mockMvc.perform(post("/api/v1/player")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"prueba\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        mockMvc.perform(delete("/api/v1/player/prueba"))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void getPlayerTest() throws Exception {
        mockMvc.perform(put("/api/v1/player")
                .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"prueba\"}"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        mockMvc.perform(post("/api/v1/player")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"prueba\"}")
        ).andExpect(MockMvcResultMatchers.status().isCreated());

        mockMvc.perform(delete("/api/v1/player/prueba"))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

}
