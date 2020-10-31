package me.angelovdejan.contacts.api.requests;

import java.util.ArrayList;

public class CreateCategoryRequest {
    public String title;
    public ArrayList<String> contacts = new ArrayList<>();
}
