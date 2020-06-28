package me.angelovdejan.contacts.api.responses;

public class CreatedContactResponse {

    private String id;

    public CreatedContactResponse(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
