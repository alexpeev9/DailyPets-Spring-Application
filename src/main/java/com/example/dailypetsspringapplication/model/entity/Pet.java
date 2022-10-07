package com.example.dailypetsspringapplication.model.entity;

import com.example.dailypetsspringapplication.model.entity.enums.PetTypeEnum;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "pets")
public class Pet  extends BaseEntity {
    private String name;
    private String description;
    private PetTypeEnum type;
    private User user;
    private String picture;

    @Column(unique = true, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Lob
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Enumerated(EnumType.STRING)
    public PetTypeEnum getType() {
        return type;
    }

    public void setType(PetTypeEnum type) {
        this.type = type;
    }

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(nullable = false, columnDefinition = "TEXT")
    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
