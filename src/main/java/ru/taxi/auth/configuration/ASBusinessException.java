package ru.taxi.auth.configuration;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@NoArgsConstructor
@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class ASBusinessException extends RuntimeException {

    public ASBusinessException(String message) {
        super(message);
    }

    public ASBusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
