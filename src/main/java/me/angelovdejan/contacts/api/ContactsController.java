package me.angelovdejan.contacts.api;

import io.swagger.annotations.Api;
import me.angelovdejan.contacts.Contact;
import me.angelovdejan.contacts.ContactsRepositoryInterface;
import me.angelovdejan.contacts.api.requests.CreateContactRequest;

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
    public List<Contact> index() {
        List<Contact> allContacts = new ArrayList<>();

        this.contactsRepository.findAll().forEach(allContacts::add);

        return allContacts;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        if (this.contactsRepository.findById(id).isEmpty()) {
            throw new ContactNotFoundException();
        }

        this.contactsRepository.deleteById(id);
    }
}
