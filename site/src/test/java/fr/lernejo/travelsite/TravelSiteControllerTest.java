package fr.lernejo.travelsite;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class TravelSiteControllerTest {

    @Autowired MockMvc mvc;

    @Test
    void inscription_response_ok() throws Exception {
        User newUser = new User("test@test.et", "Test", "FR",
            User.Weather.WARMER, 20);

        mvc
            .perform(MockMvcRequestBuilders.post("/api/inscription")
            .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(newUser)))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void travel_response_ok() throws Exception {
        mvc
            .perform(MockMvcRequestBuilders.get("/api/travels?userName=test"))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void travel_colder() throws Exception {
        User newUser = new User("test@test.et", "Test", "FR",
            User.Weather.COLDER, 20);

        mvc
            .perform(MockMvcRequestBuilders.post("/api/inscription")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(newUser)))
            .andExpect(MockMvcResultMatchers.status().isOk());

        mvc
            .perform(MockMvcRequestBuilders.get("/api/travels?userName=Test"))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void travel_warmer() throws Exception {
        User newUser = new User("test@test.et", "Test", "FR",
            User.Weather.WARMER, 20);

        mvc
            .perform(MockMvcRequestBuilders.post("/api/inscription")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(newUser)))
            .andExpect(MockMvcResultMatchers.status().isOk());

        mvc
            .perform(MockMvcRequestBuilders.get("/api/travels?userName=Test"))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
