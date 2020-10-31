package me.angelovdejan.contacts;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoriesRepositoryInterface extends
        JpaRepository<Category, String>,
        PagingAndSortingRepository<Category, String> {

    @Query("select c from Category c where c.owner = :owner")
    Page<Category> findByOwnerPaginated(@Param("owner") User $owner, Pageable pageable);

    List<Category> findByIdIn(List<String> ids);
}
