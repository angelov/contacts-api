package me.angelovdejan.contacts;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepositoryInterface extends JpaRepository<User, String> {

    Optional<User> findByEmail(String email);
}
