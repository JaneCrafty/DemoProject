package org.example.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "dogs")
public class Dog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String breed;

    @Column
    private LocalDateTime birthDate;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @OneToMany(mappedBy = "dog")
    private List<ActivityLog> activityLogs;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.name = description;
    }
    public String getDescription() {
        return name;
    }
    public String getPhotoUrl() {
        return name;
    }
    public void setPhotoUrl(String photoUrl) {
        this.name = photoUrl;
    }
    public String getStatus() {
        return name;
    }
    public void setStatus(String status) {
        this.name = status;
    }
    public String getOwnerUsername() {
        return name;
    }
    public void setOwnerUsername(String ownerUsername) {
        this.name = ownerUsername;
    }
    public String getOwnerAddress() {
        return name;
    }
    public void setOwnerAddress(String ownerAddress) {
        this.name = ownerAddress;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public LocalDateTime getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDateTime birthDate) {
        this.birthDate = birthDate;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public List<ActivityLog> getActivityLogs() {
        return activityLogs;
    }

    public void setActivityLogs(List<ActivityLog> activityLogs) {
        this.activityLogs = activityLogs;
    }
}
