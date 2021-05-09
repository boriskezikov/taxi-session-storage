package ru.taxi.auth.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.taxi.auth.domain.UserPrincipal;
import ru.taxi.auth.repository.CredentialsStorage;
import ru.taxi.auth.utils.MissingPrincipalException;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final CredentialsStorage credentialsStorage;

    public UserPrincipal authenticate(String username) {
        return credentialsStorage.findByLogin(username).orElseThrow(MissingPrincipalException::new);
    }
}
