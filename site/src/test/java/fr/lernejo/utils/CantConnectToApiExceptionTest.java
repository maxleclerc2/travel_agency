package fr.lernejo.utils;

import fr.lernejo.travelsite.Launcher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(classes = Launcher.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class CantConnectToApiExceptionTest {

    @Autowired MockMvc mvc;

    @Test
    public void cant_connect_to_api() throws Exception {
        mvc
            .perform(MockMvcRequestBuilders.get("/api/travels?userName=Test"))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
