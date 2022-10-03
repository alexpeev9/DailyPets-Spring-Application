package com.example.dailypetsspringapplication.model.binding;

import com.example.dailypetsspringapplication.model.entity.enums.PetTypeEnum;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PetBM {
    private String name;
    private String description;
    private PetTypeEnum type;
    private String picture;

    public PetBM() {
    }

    @Size(min = 3, max = 20, message = "Pet name must be between 3 and 20 characters.")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Size(min = 3) // TODO: temporary for tests
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotNull
    public PetTypeEnum getType() {
        return type;
    }

    public void setType(PetTypeEnum type) {
        this.type = type;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
