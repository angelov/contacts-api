package me.angelovdejan.contacts.api.responses;

public class CreatedUserResponse {
    private String id;

    public CreatedUserResponse(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
