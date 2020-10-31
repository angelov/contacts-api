package me.angelovdejan.contacts.api.requests;

import io.swagger.annotations.ApiModelProperty;

public class CreateContactRequest {
    @ApiModelProperty(required = true)
    public String name;

    @ApiModelProperty(required = true)
    public String email;

    @ApiModelProperty(required = true)
    public String phone;

    @ApiModelProperty(required = false)
    public Boolean favorite = false;
}
