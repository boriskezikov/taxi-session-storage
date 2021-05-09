package ru.taxi.auth.configuration;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import ru.taxi.auth.dto.ErrorDTO;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class GlobalHandler {

    @ResponseBody
    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorDTO handleApiException(Exception ex) {
        return new ErrorDTO(400, ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = {ASBusinessException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorDTO handleApiException(ASBusinessException ex) {
        return new ErrorDTO(500, ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = {EntityNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ErrorDTO handleApiException(EntityNotFoundException ex) {
        return new ErrorDTO(404, ex.getMessage());
    }
}
