package me.angelovdejan.contacts.api.requests;

import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;

public class AddContactsToCategoryRequest {
    public ArrayList<String> contacts = new ArrayList<>();
}
