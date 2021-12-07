package fr.lernejo.travelsite;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TravelSiteController {
    List<User> userList = new ArrayList<>();
    List<Travel> travelList = new ArrayList<>();

    @PostMapping("/api/inscription")
    public void inscription(@RequestBody User newUser) {
        userList.add(newUser);
    }

    @GetMapping("/api/usersTEST")
    public List<User> getUserList() {
        return userList;
    }

    @GetMapping("/api/travels")
    @ResponseBody
    public List<Travel> getUserTravels(@RequestParam String userName) {
        for (User userRegistered: userList) {
            if (userRegistered.userName().equals(userName)) {
                travelList.add(new Travel("Caribbean", 32.4));
                travelList.add(new Travel("Australia", 35.1));
            }
        }
        return travelList;
    }
}
