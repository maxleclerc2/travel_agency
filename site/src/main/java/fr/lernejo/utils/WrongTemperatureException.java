package fr.lernejo.utils;

public class WrongTemperatureException extends RuntimeException {
    public WrongTemperatureException() {
        super("Temperature incorrect.");
    }
}
