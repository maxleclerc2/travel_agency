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
        String content;
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("countries.txt");
        try {
            content = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println("Unable to read file.");
            return travelSet;
        }
        Stream<String> lines = content.lines();
        lines.forEach(country -> travelSet.add(getTravel(country)));
        return travelSet;
    }

    private Travel getTravel(String country) {
        double mean = -10.0;
        try {
            Prediction prediction = contactApi(country);

            double tempToday = prediction.temperatures().get(0).temperature();
            double tempYesterday = prediction.temperatures().get(1).temperature();

            mean = (tempToday + tempYesterday) / 2;
        } catch (CantConnectToApiException e) {
            System.out.println(e.getMessage());
        }
        return new Travel(country, mean);
    }

    private Prediction contactApi(String country) throws CantConnectToApiException {
        Call<Prediction> call = predictionAPI.getPrediction(country);
        try {
            return call.execute().body();
        } catch (IOException e) {
            throw new CantConnectToApiException();
        }
    }
}
