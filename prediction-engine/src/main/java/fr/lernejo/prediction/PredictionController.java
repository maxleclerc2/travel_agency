package fr.lernejo.prediction;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
public class PredictionController {

    private final TemperatureService service = new TemperatureService();

    @GetMapping("/api/temperature")
    @ResponseBody
    public Prediction getTemperature(@RequestParam String country) {
        List<Temperature> temperaturesList = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            LocalDate date = LocalDate.now().minusDays(i);
            temperaturesList.add(
                new Temperature(
                    date,
                    service.getTemperature(country)
                )
            );
        }

        return new Prediction(
            country,
            temperaturesList
        );
    }
}
