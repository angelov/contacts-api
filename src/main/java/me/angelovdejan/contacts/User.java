package me.angelovdejan.contacts;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
public class User implements UserDetails {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Contact> contacts = new HashSet<>();

    public User(String email, String password) {
        this.id = UUID.randomUUID().toString();
        this.email = email;
        this.password = password;
    }

    public User() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    public String getId() { return id; }

    @Override
    public String getUsername() {
        return getEmail();
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Set<Contact> getContacts() {
        return contacts;
    }
}
