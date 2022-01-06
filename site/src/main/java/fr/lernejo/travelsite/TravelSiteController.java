package fr.lernejo.travelsite;

import fr.lernejo.travelsite.prediction.PredictionService;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
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

        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("testing.txt", true), StandardCharsets.UTF_8));
            writer.append("\nUSER INFO :\n").append(user.weatherExpectation().toString()).append("\n");
            writer.append(user.userCountry()).append("\n");
            writer.append(Double.toString(userTemp)).append("\n\n");

            for (Travel t: travelSet) {
                writer.append(t.country()).append("\n");
            }

            writer.append("\nTRAVELS :\n");

            for (Travel travel : travelSet) {
                /*
                if (user.weatherExpectation().equals(User.Weather.COLDER) && Double.compare(travel.temperature(), userTemp) < 0)
                    travelList.add(travel);
                else if (user.weatherExpectation().equals(User.Weather.WARMER) && Double.compare(travel.temperature(), userTemp) > 0)
                    travelList.add(travel);
                 */
                writer.append(travel.country()).append(" - ").append(Double.toString(travel.temperature())).append("\n");

                switch (user.weatherExpectation()) {
                    case COLDER -> {
                        writer.append("COLDER\n");
                        if (Double.compare(travel.temperature(), userTemp) < 0) {
                            writer.append("ADDED\n");
                            travelList.add(travel);
                        } else {
                            writer.append("REJECTED\n");
                        }
                    }
                    case WARMER -> {
                        writer.append("WARMER\n");
                        if (Double.compare(travel.temperature(), userTemp) > 0) {
                            writer.append("ADDED\n");
                            travelList.add(travel);
                        } else {
                            writer.append("REJECTED\n");
                        }
                    }
                }
            }

            writer.close();
        } catch (Exception e) {
            //
        }

        return travelList;
    }

    private double userCountryTemp(String country, Set<Travel> travels) {
        for (Travel t: travels) {
            if (t.country().toLowerCase(Locale.ROOT).equals(country.toLowerCase(Locale.ROOT)))
                return t.temperature();
        }

        return -10.0;
    }
}
