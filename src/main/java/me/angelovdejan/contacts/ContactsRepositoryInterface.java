package me.angelovdejan.contacts;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ContactsRepositoryInterface extends
        CrudRepository<Contact, String>,
        PagingAndSortingRepository<Contact, String> {
}
