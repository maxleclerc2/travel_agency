package fr.lernejo.travelsite;

import fr.lernejo.utils.WrongEmailException;
import fr.lernejo.utils.WrongTemperatureException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class UserTest {
    @Test
    public void wrong_email() {
        Assertions.assertThatExceptionOfType(WrongEmailException.class)
            .isThrownBy(() -> new User(".test@test.et", "Test", "FR",
                User.Weather.WARMER, 20));

        Assertions.assertThatExceptionOfType(WrongEmailException.class)
            .isThrownBy(() -> new User("test@test@et", "Test", "FR",
                User.Weather.WARMER, 20));

        Assertions.assertThatExceptionOfType(WrongEmailException.class)
            .isThrownBy(() -> new User(".test@test", "Test", "FR",
                User.Weather.COLDER, 20));

        Assertions.assertThatExceptionOfType(WrongEmailException.class)
            .isThrownBy(() -> new User("test", "Test", "FR",
                User.Weather.COLDER, 20));
    }

    @Test
    public void wrong_temperature() {
        Assertions.assertThatExceptionOfType(WrongTemperatureException.class)
            .isThrownBy(() -> new User("test@test.et", "Test", "FR",
                User.Weather.WARMER, -5));

        Assertions.assertThatExceptionOfType(WrongTemperatureException.class)
            .isThrownBy(() -> new User("test@test.et", "Test", "FR",
                User.Weather.COLDER, 45));
    }

    @Test
    public void right_input() {
        //TODO
        // Faire un meilleur test

        User userToTest = new User("test@test.et", "Test", "FR",
            User.Weather.WARMER, 20);

        Assertions.assertThat(userToTest.userEmail())
            .as("Email")
            .isEqualTo("test@test.et");

        Assertions.assertThat(userToTest.userName())
            .as("Name")
            .isEqualTo("Test");

        Assertions.assertThat(userToTest.userCountry())
            .as("Country")
            .isEqualTo("FR");

        Assertions.assertThat(userToTest.weatherExpectation())
            .as("Weather")
            .isEqualTo(User.Weather.WARMER);

        Assertions.assertThat(userToTest.minimumTemperatureDistance())
            .as("Temperature")
            .isEqualTo(20);
    }
}
