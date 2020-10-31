package me.angelovdejan.contacts.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.angelovdejan.contacts.*;
import me.angelovdejan.contacts.api.requests.AddContactsToCategoryRequest;
import me.angelovdejan.contacts.api.requests.CreateCategoryRequest;
import me.angelovdejan.contacts.api.responses.CreatedCategoryResponse;
import me.angelovdejan.contacts.api.responses.ItemsList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/categories")
@Api(tags = "Categories", description = "Managing the list of categories")
public class CategoriesController {
    private final CategoriesRepositoryInterface categories;
    private final ContactsRepositoryInterface contacts;

    public CategoriesController(CategoriesRepositoryInterface categories, ContactsRepositoryInterface contacts) {
        this.categories = categories;
        this.contacts = contacts;
    }

    @PostMapping(path = "/")
    @ApiOperation(value = "Creating a new category.")
    public ResponseEntity<CreatedCategoryResponse> createContact(@RequestBody CreateCategoryRequest request) {
        User owner = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Category category = new Category(
                request.title,
                owner
        );

        contacts.findByIdIn(request.contacts).forEach(category::addContact);

        this.categories.save(category);

        return ResponseEntity.ok(new CreatedCategoryResponse(category.getId()));
    }

    @GetMapping(path = "/")
    @ApiOperation(value = "Listing the user's categories.")
    public ResponseEntity<ItemsList<Category>> index(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer count
    ) {
        Pageable pageable = PageRequest.of(page - 1, count);
        User owner = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Page<Category> pageResult = this.categories.findByOwnerPaginated(owner, pageable);

        return ResponseEntity.ok(ItemsList.fromPage(pageResult));
    }

    @GetMapping(path = "/{id}/contacts")
    @ApiOperation(value = "Listing the contacts in a category.")
    public ResponseEntity<ItemsList<Contact>> contacts(@RequestParam String id) {
        Optional<Category> result = this.categories.findById(id);

        if (result.isEmpty()) {
            throw new CategoryNotFoundException();
        }

        Category category = result.get();

        return ResponseEntity.ok(ItemsList.fromItems(new ArrayList<>(category.getContacts())));
    }

    @PostMapping(path = "/{id}/contacts")
    @ApiOperation(value = "Adding contacts to a category.")
    public void contacts(@PathVariable String id, @RequestBody AddContactsToCategoryRequest request) {
        Optional<Category> result = this.categories.findById(id);

        if (result.isEmpty()) {
            throw new CategoryNotFoundException();
        }

        Category category = result.get();

        this.contacts.findByIdIn(request.contacts).forEach(category::addContact);

        this.categories.save(category);
    }

    @DeleteMapping(path = "/{id}/contacts")
    @ApiOperation(value = "Deleting contacts from a category.")
    public void deleteContacts(@PathVariable String id, @RequestBody AddContactsToCategoryRequest request) {
        Optional<Category> result = this.categories.findById(id);

        if (result.isEmpty()) {
            throw new CategoryNotFoundException();
        }

        Category category = result.get();

        this.contacts.findByIdIn(request.contacts).forEach(category::removeContact);

        this.categories.save(category);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deleting a category.")
    public void delete(@PathVariable String id) {
        if (this.categories.findById(id).isEmpty()) {
            throw new CategoryNotFoundException();
        }

        this.categories.deleteById(id);
    }
}
