package fr.lernejo.travelsite;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.lernejo.travelsite.prediction.Prediction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.junit.jupiter.MockServerExtension;
import org.mockserver.junit.jupiter.MockServerSettings;
import org.mockserver.model.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.LinkedList;
import java.util.List;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ExtendWith(MockServerExtension.class)
@MockServerSettings(ports = {7080, 7081})
class TravelSiteControllerTest {

    @Autowired MockMvc mvc;
    private final ClientAndServer mockServer;

    public TravelSiteControllerTest(ClientAndServer mockServer) {
        this.mockServer = mockServer;
    }

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

    @Test
    public void travel_colder() throws Exception {
        createMockServerClient();

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
        createMockServerClient();

        User newUser = new User("test@test.et", "Test", "FR",
            User.Weather.WARMER, 10);

        mvc
            .perform(MockMvcRequestBuilders.post("/api/inscription")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(newUser)))
            .andExpect(MockMvcResultMatchers.status().isOk());

        mvc
            .perform(MockMvcRequestBuilders.get("/api/travels?userName=Test"))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    private void createMockServerClient() {
        new MockServerClient("localhost", 7080)
            .when(
                request()
                    .withMethod("GET")
                    .withPath("/api/temperature"))
            .respond(
                response()
                    .withStatusCode(200)
                    .withHeaders(
                        new Header("Content-Type", "application/json"))
                    .withBody(asJsonString(mockPrediction()))
            );
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Prediction mockPrediction() {
        List<Prediction.Temperature> tempList = new LinkedList<>();
        tempList.add(new Prediction.Temperature("2021-12-04", 20));
        tempList.add(new Prediction.Temperature("2021-12-03", 10));
        return new Prediction("France", tempList);
    }
}
