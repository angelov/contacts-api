package me.angelovdejan.contacts.api;

import io.swagger.annotations.*;
import me.angelovdejan.contacts.Contact;
import me.angelovdejan.contacts.ContactsRepositoryInterface;
import me.angelovdejan.contacts.User;
import me.angelovdejan.contacts.api.requests.CreateContactRequest;

import me.angelovdejan.contacts.api.requests.FavoriteContactRequest;
import me.angelovdejan.contacts.api.requests.UpdateContactRequest;
import me.angelovdejan.contacts.api.responses.CreatedContactResponse;
import me.angelovdejan.contacts.api.responses.ItemsList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/contacts")
@Api(tags = "Contacts", description = "Managing the list of contacts")
public class ContactsController {

    private ContactsRepositoryInterface contactsRepository;

    public ContactsController(ContactsRepositoryInterface contactsRepository) {
        this.contactsRepository = contactsRepository;
    }

    @PostMapping(path = "/")
    public ResponseEntity<CreatedContactResponse> createContact(@RequestBody CreateContactRequest request) {

        String id = UUID.randomUUID().toString();
        User owner = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Contact contact = new Contact(
                id,
                owner,
                request.name,
                request.phone,
                request.email
        );

        contact.setFavorite(request.favorite);

        this.contactsRepository.save(contact);

        return ResponseEntity.ok(new CreatedContactResponse(id));
    }

    @GetMapping(path = "/")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Listing the contacts.")
    })
    public ResponseEntity<ItemsList<Contact>> index(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer count
    ) {
        Pageable pageable = PageRequest.of(page - 1, count);
        User owner = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Page<Contact> pageResult = this.contactsRepository.findByOwnerPaginated(owner, pageable);

        return ResponseEntity.ok(ItemsList.fromPage(pageResult));
    }

    @PutMapping(path = "/{id}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Updating a contact.")
    })
    public void index(@PathVariable String id, @RequestBody UpdateContactRequest request) {
        Optional<Contact> result = this.contactsRepository.findById(id);

        if (result.isEmpty()) {
            throw new ContactNotFoundException();
        }

        Contact contact = result.get();

        contact.setName(request.name);
        contact.setPhoneNumber(request.phone);
        contact.setEmailAddress(request.email);

        if (request.favorite != null) {
            contact.setFavorite(request.favorite);
        }

        this.contactsRepository.save(contact);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        if (this.contactsRepository.findById(id).isEmpty()) {
            throw new ContactNotFoundException();
        }

        this.contactsRepository.deleteById(id);
    }

    @PostMapping(path = "/{id}/favorite")
    @ApiOperation(value = "Favoriting/Unfavoriting a contact.")
    public void favoriteContact(@PathVariable String id, @RequestBody FavoriteContactRequest request) {
        Optional<Contact> result = this.contactsRepository.findById(id);

        if (result.isEmpty()) {
            throw new ContactNotFoundException();
        }

        Contact contact = result.get();

        contact.setFavorite(request.favorite);

        this.contactsRepository.save(contact);
    }

    @GetMapping(path = "/favorite")
    @ApiOperation(value = "Listing the user's favorite contacts.")
    public ResponseEntity<ItemsList<Contact>> favoriteContacts(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer count
    ) {
        Pageable pageable = PageRequest.of(page - 1, count);
        User owner = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Page<Contact> pageResult = this.contactsRepository.favoriteByOwner(owner, pageable);

        return ResponseEntity.ok(ItemsList.fromPage(pageResult));
    }
}
