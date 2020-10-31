package me.angelovdejan.contacts.api.responses;

public class CreatedCategoryResponse {

    private String id;

    public CreatedCategoryResponse(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
