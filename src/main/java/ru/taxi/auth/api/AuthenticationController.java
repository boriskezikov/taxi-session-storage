package ru.taxi.auth.api;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.taxi.auth.security.AuthenticationRequest;
import ru.taxi.auth.security.AccessTokenResponse;
import ru.taxi.auth.security.AccessTokenUtil;
import ru.taxi.auth.security.AccessTokenUserDetailsService;
import ru.taxi.auth.utils.AuthorizationException;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("api/v1/auth/session")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final AccessTokenUtil accessTokenUtil;
    private final AccessTokenUserDetailsService userDetailsService;
    private final HttpServletRequest request;

    @PostMapping
    public AccessTokenResponse createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = accessTokenUtil.generateToken(userDetails);
        return new AccessTokenResponse(token);
    }

    @PostMapping("/validate")
    public UsernamePasswordAuthenticationToken validateAuthentication(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        String username;
        String accessToken;
        if (authorization != null && authorization.startsWith("Bearer ")) {
            accessToken = authorization.substring(7);
            try {
                username = accessTokenUtil.getUsernameFromToken(accessToken);
            } catch (IllegalArgumentException e) {
                throw new AuthorizationException("Unable to parse access token");
            } catch (ExpiredJwtException e) {
                throw new AuthorizationException("Token expired");
            }
        } else {
            throw new AuthorizationException("Access token does not begin with Bearer String");
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (accessTokenUtil.validateToken(accessToken, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                log.info("Authenticated: {}", usernamePasswordAuthenticationToken);
                return usernamePasswordAuthenticationToken;
            }
        }
        throw new AuthorizationException("Authorization error");
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
