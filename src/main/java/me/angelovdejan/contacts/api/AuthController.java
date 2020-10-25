package me.angelovdejan.contacts.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.angelovdejan.contacts.User;
import me.angelovdejan.contacts.api.requests.AuthenticationRequest;
import me.angelovdejan.contacts.api.responses.AuthenticationResponse;
import me.angelovdejan.contacts.api.responses.UserResponse;
import me.angelovdejan.contacts.api.security.JwtTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/auth")
@Api(tags = "Authentication", description = "User authentication")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> logIn(@RequestBody AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

            String token = tokenProvider.createToken(request.getEmail(), new ArrayList<>());

            return ResponseEntity.ok(new AuthenticationResponse(token));
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password.");
        }
    }

    @GetMapping("/me")
    @ApiOperation(value = "Returns information about the authenticated user.")
    public ResponseEntity<UserResponse> me() {
        User owner = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return ResponseEntity.ok(UserResponse.fromUser(owner));
    }
}
