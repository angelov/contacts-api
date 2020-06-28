package me.angelovdejan.contacts;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SampleDataSeeder implements ApplicationListener<ApplicationReadyEvent> {

    private UserRepositoryInterface users;
    private PasswordEncoder passwordEncoder;

    public SampleDataSeeder(UserRepositoryInterface userRepositoryInterface, PasswordEncoder passwordEncoder) {
        this.users = userRepositoryInterface;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {

        users.save(new User("angela@example.com", encodePassword("123456")));
        users.save(new User("dejan@example.com", encodePassword("123456")));
    }

    private String encodePassword(String raw) {
        return passwordEncoder.encode(raw);
    }
}
