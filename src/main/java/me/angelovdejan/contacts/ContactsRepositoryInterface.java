package me.angelovdejan.contacts;

import org.springframework.data.repository.CrudRepository;

public interface ContactsRepositoryInterface extends CrudRepository<Contact, String> {
}
