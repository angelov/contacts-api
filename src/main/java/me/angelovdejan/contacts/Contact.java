package me.angelovdejan.contacts;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Contact {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email_address")
    private String emailAddress;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_id", nullable = false)
    @JsonIgnore
    private User owner;

    @Column(name = "favorite")
    private Boolean favorite;

    @ManyToMany(mappedBy = "contacts", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Category> categories = new HashSet<>();

    public Contact() {
    }

    public Contact(String id, User owner, String name, String phoneNumber, String emailAddress) {
        this.id = id;
        this.owner = owner;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.favorite = false;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public User getOwner() {
        return owner;
    }

    public Boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    public Set<Category> getCategories() {
        return this.categories;
    }

    public void clearCategories() {
        this.categories.clear();
    }

    public void addCategory(Category category) {
        this.categories.add(category);
    }
}
