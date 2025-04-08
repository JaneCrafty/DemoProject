package org.example.model;

import jakarta.persistence.*;

@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long dogId;

    @Column(nullable = false)
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String type;      // тип действия: гулять, играть, учиться
    private String message;   // текст уведомления
    private boolean accepted; // принятие действия

    public Long getDogId() {
        return dogId;
    }

    public void setDogId(Long dogId) {
        this.dogId = dogId;
    }

    @ManyToOne
    private Dog senderDog;

    @ManyToOne
    private Dog receiverDog;

    public Long getId() { return id; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public boolean isAccepted() { return accepted; }
    public void setAccepted(boolean accepted) { this.accepted = accepted; }

    public Dog getSenderDog() { return senderDog; }
    public void setSenderDog(Dog senderDog) { this.senderDog = senderDog; }

    public Dog getReceiverDog() { return receiverDog; }
    public void setReceiverDog(Dog receiverDog) { this.receiverDog = receiverDog; }
}
