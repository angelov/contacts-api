package me.angelovdejan.contacts.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.angelovdejan.contacts.User;
import me.angelovdejan.contacts.UserRepositoryInterface;
import me.angelovdejan.contacts.api.requests.CreateUserRequest;
import me.angelovdejan.contacts.api.responses.CreatedUserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@Api(tags = "Users", description = "Managing the list of users")
public class UsersController {
    private final UserRepositoryInterface users;
    private final PasswordEncoder passwordEncoder;

    public UsersController(UserRepositoryInterface users, PasswordEncoder passwordEncoder) {
        this.users = users;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(path = "/")
    @ApiOperation(value = "Creating a new user.")
    public ResponseEntity<CreatedUserResponse> createContact(@RequestBody CreateUserRequest request) {
        User user = new User(
                request.email,
                passwordEncoder.encode(request.password)
        );

        this.users.save(user);

        return ResponseEntity.ok(new CreatedUserResponse(user.getId()));
    }
}
