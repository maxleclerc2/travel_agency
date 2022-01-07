package fr.lernejo.travelsite;

import fr.lernejo.travelsite.prediction.PredictionService;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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

        for (User userRegistered: userList) {
            if (userRegistered.userName().equals(userName)) {
                travelList = userTravels(userRegistered);
            }
        }

        return travelList;
    }

    private List<Travel> userTravels(User user) {
        Set<Travel> travelSet = service.callApi();
        List<Travel> travelList = new LinkedList<>();
        double userTemp = userCountryTemp(user.userCountry(), travelSet);
        for (Travel travel : travelSet) {
            if (Double.compare(travel.temperature(), -10.0) == 0) continue; // Can't connect to API ?
            switch (user.weatherExpectation()) {
                case COLDER -> {
                    if (Double.compare(travel.temperature(), userTemp) < 0) travelList.add(travel);
                }
                case WARMER -> {
                    if (Double.compare(travel.temperature(), userTemp) > 0) travelList.add(travel);
                }
            }
        }
        return travelList;
    }

    private double userCountryTemp(String country, Set<Travel> travels) {
        for (Travel t: travels) {
            if (t.country().toLowerCase(Locale.ROOT).equals(country.toLowerCase(Locale.ROOT)))
                return t.temperature();
        }

        return -10.0; // Shouldn't happen
    }
}
