package org.example.service;

import org.example.dto.DogInteractionRequest;
import org.example.dto.DogProfileDto;
import org.example.model.Dog;
import org.example.model.Notification;
import org.example.model.Owner;
import org.example.repository.DogProfileRepository;
import org.example.repository.NotificationRepository;
import org.example.repository.OwnerRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.example.dto.NotificationDto;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {
    private final ActivityLogService logService;

    private final NotificationRepository notificationRepo;
    private final DogProfileRepository dogRepo;
    private final OwnerRepository ownerRepo;
    private final SimpMessagingTemplate messagingTemplate;

    public NotificationService(ActivityLogService logService,
                               NotificationRepository notificationRepo,
                               DogProfileRepository dogRepo,
                               OwnerRepository ownerRepo,
                               SimpMessagingTemplate messagingTemplate) {
        this.notificationRepo = notificationRepo;
        this.dogRepo = dogRepo;
        this.ownerRepo = ownerRepo;
        this.logService = logService;
        this.messagingTemplate = messagingTemplate;
    }

    public void sendInteraction(DogInteractionRequest request) {
        Owner current = getCurrentOwner();

        Dog senderDog = dogRepo.findById(request.fromDogId).orElseThrow();
        Dog receiverDog = dogRepo.findById(request.toDogId).orElseThrow();

        if (!senderDog.getOwner().getId().equals(current.getId())) {
            throw new RuntimeException("You can only act from your own dog.");
        }

        Notification n = new Notification();
        n.setSenderDog(senderDog);
        n.setReceiverDog(receiverDog);
        n.setType(request.actionType);
        n.setAccepted(false);
        n.setMessage("Собака " + senderDog.getName() + " зовёт собаку " + receiverDog.getName() + " на " + request.actionType);

        notificationRepo.save(n);

        logService.log("INVITE", senderDog, receiverDog, request.actionType);

        NotificationDto dto = mapToDto(n);
        Long ownerId = receiverDog.getOwner().getId();
        messagingTemplate.convertAndSend("/topic/notify/" + ownerId, dto);
    }

    public List<NotificationDto> getNotificationsForCurrentUser() {
        Owner current = getCurrentOwner();

        return notificationRepo.findAll().stream()
                .filter(n -> n.getReceiverDog().getOwner().getId().equals(current.getId()))
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public void accept(Long id, String actionType) {
        Owner current = getCurrentOwner();
        Notification n = notificationRepo.findById(id).orElseThrow();

        if (!n.getReceiverDog().getOwner().getId().equals(current.getId())) {
            throw new RuntimeException("Not your notification");
        }

        n.setAccepted(true);
        notificationRepo.save(n);

        logService.log("INVITE", n.getSenderDog(), n.getReceiverDog(), actionType);

        Dog receiver = n.getReceiverDog();
        receiver.setStatus(n.getType());
        dogRepo.save(receiver);

        logService.log("ACCEPT", receiver, n.getSenderDog(), n.getType());
    }

    public void reject(Long id) {
        Owner current = getCurrentOwner();
        Notification n = notificationRepo.findById(id).orElseThrow();

        if (!n.getReceiverDog().getOwner().getId().equals(current.getId())) {
            throw new RuntimeException("Not your notification");
        }

        notificationRepo.delete(n);
    }

    private NotificationDto mapToDto(Notification n) {
        NotificationDto dto = new NotificationDto();
        dto.id = n.getId();
        dto.message = n.getMessage();
        dto.actionType = n.getType();
        dto.dogName = n.getReceiverDog().getName();
        dto.senderDog = n.getSenderDog().getName();
        dto.accepted = n.isAccepted();
        return dto;
    }

    private Owner getCurrentOwner() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return ownerRepo.findByUsername(username).orElseThrow();
    }
}
