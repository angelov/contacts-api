package me.angelovdejan.contacts;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContactsRepositoryInterface extends
        CrudRepository<Contact, String>,
        PagingAndSortingRepository<Contact, String> {

    @Query("select c from Contact c where c.owner = :owner")
    Page<Contact> findByOwnerPaginated(@Param("owner") User $owner, Pageable pageable);


    @Query("select c from Contact c where c.owner = :owner and c.favorite = true")
    Page<Contact> favoriteByOwner(@Param("owner") User $owner, Pageable pageable);

    List<Contact> findByIdIn(List<String> ids);
}
