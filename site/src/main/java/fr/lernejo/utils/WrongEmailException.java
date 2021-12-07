package fr.lernejo.utils;

public class WrongEmailException extends RuntimeException {
    public WrongEmailException() {
        super("Email incorrect.");
    }
}
