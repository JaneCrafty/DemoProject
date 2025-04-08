package org.example.dto;

import org.example.model.Owner;

import java.util.List;

public class DogProfileDto {
    public Long id;
    public String name;
    public String description;
    public String photoUrl;
    public String status;

    public String ownerUsername;
    public String ownerAddress;

    public List<String> friendNames;

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setOwnerUsername(Owner owner) {
        this.ownerUsername = String.valueOf(owner);
    }

    public void setOwnerAddress(Owner owner) {
        this.ownerAddress = String.valueOf(owner);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getStatus() {
        return status;
    }
}
