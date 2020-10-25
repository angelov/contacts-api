package me.angelovdejan.contacts.api.responses;

import me.angelovdejan.contacts.User;

public class UserResponse {
    public String email;

    public static UserResponse fromUser(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.email = user.getEmail();

        return userResponse;
    }
}
