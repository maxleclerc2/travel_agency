package fr.lernejo.travelsite;

import fr.lernejo.travelsite.prediction.PredictionService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@RestController
public class TravelSiteController {
    private final PredictionService service;
    private final List<User> userList = new ArrayList<>();

    public TravelSiteController(PredictionService service) {
        this.service = service;
    }

    @PostMapping("/api/inscription")
    public void inscription(@RequestBody User newUser) {
        userList.add(newUser);
    }

    @GetMapping("/api/travels")
    @ResponseBody
    public List<Travel> getUserTravels(@RequestParam String userName) {
        List<Travel> travelList = new LinkedList<>();
        Set<Travel> travelSet = service.callApi();
        for (User userRegistered: userList) {
            if (userRegistered.userName().equals(userName)) {
                double userTemp = userCountryTemp(userRegistered.userCountry(), travelSet);
                for (Travel travel: travelSet) {
                    if (userRegistered.weatherExpectation().equals(User.Weather.COLDER) && travel.temperature() < userTemp) // && travel.temperature() >= userTemp - userRegistered.minimumTemperatureDistance())
                        travelList.add(travel);
                    else if (userRegistered.weatherExpectation().equals(User.Weather.WARMER) && travel.temperature() > userTemp) // && travel.temperature() <= userTemp + userRegistered.minimumTemperatureDistance())
                        travelList.add(travel);
                }
            }
        }
        return travelList;
    }

    private double userCountryTemp(String country, Set<Travel> travels) {
        for (Travel t: travels) {
            if (t.country().equals(country))
                return t.temperature();
        }

        return 0.0;
    }
}
