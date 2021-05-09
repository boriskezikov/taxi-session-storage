package ru.taxi.auth.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AccessTokenResponse {
    private static final long serialVersionUID = -8091879091924046844L;

    private final String token;
}

