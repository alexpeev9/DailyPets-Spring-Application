package com.example.dailypetsspringapplication.model.binding;

import com.example.dailypetsspringapplication.model.entity.enums.PetTypeEnum;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class PetBM {
    private String name;
    private String description;
    private PetTypeEnum type;
    private String picture;

    public PetBM() {
    }

    @Size(min = 3, max = 20, message = "Name must be between 3 and 20 characters!")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Size(min = 3, max=100, message = "Description must be between 3 and 100 characters!") // TODO: temporary for tests
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotNull(message = "You should select a type!")
    public PetTypeEnum getType() {
        return type;
    }

    public void setType(PetTypeEnum type) {
        this.type = type;
    }

    @NotNull(message = "You should provide an url!")
    @Pattern(regexp="^https:\\/\\/[^\\s]+", message="Url must start with https://")
    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
