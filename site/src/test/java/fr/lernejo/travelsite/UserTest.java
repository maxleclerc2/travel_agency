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
}
