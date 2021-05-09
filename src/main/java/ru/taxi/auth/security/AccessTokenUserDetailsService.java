package ru.taxi.auth.security;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.taxi.auth.domain.UserPrincipal;
import ru.taxi.auth.services.AuthService;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AccessTokenUserDetailsService implements UserDetailsService {

    private final AuthService authService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserPrincipal user = authService.authenticate(username);
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(),
                new ArrayList<>());
    }


}

