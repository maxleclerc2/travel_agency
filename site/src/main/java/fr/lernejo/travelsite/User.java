package fr.lernejo.travelsite;

import fr.lernejo.utils.WrongEmailException;
import fr.lernejo.utils.WrongTemperatureException;

public record User(String userEmail, String userName, String userCountry,
                   fr.lernejo.travelsite.User.Weather weatherExpectation, double minimumTemperatureDistance) {

    public User(String userEmail, String userName, String userCountry, Weather weatherExpectation, double minimumTemperatureDistance) {
        String emailRegexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        if (!userEmail.matches(emailRegexPattern)) throw new WrongEmailException();
        else this.userEmail = userEmail;

        if (minimumTemperatureDistance < 0 || minimumTemperatureDistance > 40) throw new WrongTemperatureException();
        else this.minimumTemperatureDistance = minimumTemperatureDistance;

        this.userName = userName;
        this.userCountry = userCountry;
        this.weatherExpectation = weatherExpectation;
    }

    public enum Weather {
        WARMER,
        COLDER
    }
}
