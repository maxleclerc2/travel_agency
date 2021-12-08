package fr.lernejo.travelsite.prediction;

import fr.lernejo.travelsite.Travel;
import fr.lernejo.utils.CantConnectToApiException;
import org.springframework.stereotype.Service;
import retrofit2.Call;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

@Service
public class PredictionService {

    private final PredictionAPI predictionAPI;

    public PredictionService(PredictionAPI predictionAPI) {
        this.predictionAPI = predictionAPI;
    }

    public Set<Travel> callApi() {
        Set<Travel> travelSet = new HashSet<>();

        try {
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("countries.txt");
            String content = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            Stream<String> lines = content.lines();

            lines.forEach(country -> travelSet.add(getTravel(country)));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ah sh*t, here we go again");
        }

        return travelSet;
    }

    public Travel getTravel(String country) {
        Prediction prediction = contactApi(country);

        double tempToday = prediction.temperatures().get(0).temperature();
        double tempYesterday = prediction.temperatures().get(1).temperature();

        double mean = (tempToday + tempYesterday) / 2;

        return new Travel(country, mean);
    }

    private Prediction contactApi(String country) {
        Call<Prediction> call = predictionAPI.getPrediction(country);

        try {
            return call.execute().body();
        } catch (IOException e) {
            throw new CantConnectToApiException();
        }
    }
}
