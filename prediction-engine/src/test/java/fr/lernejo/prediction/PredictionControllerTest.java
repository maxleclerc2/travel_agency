package fr.lernejo.prediction;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class PredictionControllerTest {

    @Autowired MockMvc mvc;

    @Test
    public void unknown_country() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/temperature?country=toto"))
            .andExpect(MockMvcResultMatchers.status().isExpectationFailed());
    }

    @Test
    public void known_country() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/temperature?country=france"))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
