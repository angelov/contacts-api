package me.angelovdejan.contacts.api;

import io.swagger.annotations.*;
import me.angelovdejan.contacts.Contact;
import me.angelovdejan.contacts.ContactsRepositoryInterface;
import me.angelovdejan.contacts.api.requests.CreateContactRequest;

import me.angelovdejan.contacts.api.responses.ItemsList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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
    public Map<String, String> createContact(@RequestBody CreateContactRequest request) {

        String id = UUID.randomUUID().toString();

        Contact contact = new Contact(
                id,
                request.name,
                request.phone,
                request.email
        );

        this.contactsRepository.save(contact);

        Map<String, String> response = new HashMap<>();
        response.put("id", id);

        return response;
    }

    @GetMapping(path = "/")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Listing the contacts.")
    })
    public ResponseEntity<ItemsList<Contact>> index(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer count
    ) {
        Pageable pageable = PageRequest.of(page, count);
        Page pageResult = this.contactsRepository.findAll(pageable);

        return ResponseEntity.ok(ItemsList.fromPage(pageResult));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        if (this.contactsRepository.findById(id).isEmpty()) {
            throw new ContactNotFoundException();
        }

        this.contactsRepository.deleteById(id);
    }
}
