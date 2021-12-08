package fr.lernejo.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.I_AM_A_TEAPOT)
public class CantConnectToApiException extends RuntimeException {
    public CantConnectToApiException() {
        super("Unable to connect to the Prediction API.");
    }
}
