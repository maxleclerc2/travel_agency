package fr.lernejo.prediction;

import java.util.List;

public record Prediction(String country, List<Temperature> temperatures) {
}
