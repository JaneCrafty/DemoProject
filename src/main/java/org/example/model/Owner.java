package org.example.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Owner {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;
    private String email;

    @Column(nullable = false)
    private String password;
    private String role; // owner, admin

    private String address;

    @Column(nullable = false)
    private String city;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    // Геттеры и сеттеры
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Dog> dogs;

    @ManyToMany
    private List<Owner> friends;

    // Getters and Setters
    public Long getId() { return id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public List<Dog> getDogs() { return dogs; }
    public void setDogs(List<Dog> dogs) { this.dogs = dogs; }

    public List<Owner> getFriends() { return friends; }
    public void setFriends(List<Owner> friends) { this.friends = friends; }
}
