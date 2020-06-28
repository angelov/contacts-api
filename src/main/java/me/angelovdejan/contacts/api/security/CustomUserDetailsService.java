package me.angelovdejan.contacts.api.security;

import me.angelovdejan.contacts.UserRepositoryInterface;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@Primary
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepositoryInterface users;

    public CustomUserDetailsService(UserRepositoryInterface users) {
        this.users = users;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return this.users
                .findByEmail(s)
                .orElseThrow(() -> new UsernameNotFoundException("Username: " + s + " not found"));
    }
}
