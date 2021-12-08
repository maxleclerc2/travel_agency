package fr.lernejo.prediction;

import java.time.LocalDate;
import java.util.List;

public record Prediction(String country, List<Temperature> temperatures) {

    public record Temperature(LocalDate date, double temperature) {
    }
}
