package ru.taxi.auth.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Missing principal for given credentials. Session locked.")
public class MissingPrincipalException extends RuntimeException {
}
