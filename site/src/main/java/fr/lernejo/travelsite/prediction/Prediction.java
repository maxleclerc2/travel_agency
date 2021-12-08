package fr.lernejo.travelsite.prediction;

import java.util.List;

public record Prediction(String country, List<Temperature> temperatures) {

    public record Temperature(String date, double temperature) {
    }
}
