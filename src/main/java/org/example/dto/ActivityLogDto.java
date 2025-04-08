package org.example.dto;

import org.example.model.ActivityLog;
import org.example.model.Dog;

import java.time.LocalDateTime;

public class ActivityLogDto extends ActivityLog {
    public Long id;
    public String type;         // "INVITE", "ACCEPT"
    public String actorDogName;
    public String targetDogName;
    public String action;       // "гулять", "играть", "учиться"
    public LocalDateTime timestamp;

    public void setType(String type) {
        this.type = type;
    }

    public void setActorDog(Dog actorDogName) {
        this.actorDogName = actorDogName.toString();
    }

    public void setTargetDog(Dog targetDogName) {
        this.targetDogName = targetDogName.toString();
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
