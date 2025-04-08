package org.example.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "activity_logs")
public class ActivityLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "dog_id")
    private Dog dog;

    @ManyToOne
    @JoinColumn(name = "actor_dog_id")
    private Dog actorDog;

    @ManyToOne
    @JoinColumn(name = "target_dog_id")
    private Dog targetDog;

    @Column(nullable = false)
    private String actionType;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Dog getActorDog() {
        return dog;
    }

    public void setActorDog(Dog dog) {
        this.dog = dog;
    }

    public Dog getTargetDog() {
        return targetDog;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
