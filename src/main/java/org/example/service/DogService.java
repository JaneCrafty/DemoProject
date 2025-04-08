package org.example.service;

import org.example.dto.CreateDogRequest;
import org.example.dto.UpdateDogRequest;
import org.example.model.Dog;
import org.example.model.Owner;
import org.example.repository.DogProfileRepository;
import org.example.repository.OwnerRepository;
import org.springframework.stereotype.Service;
import org.example.dto.DogProfileDto;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DogService {
    private final DogProfileRepository dogRepo;
    private final OwnerRepository ownerRepo;

    private Owner getCurrentOwner() {
        return ownerRepo.findByUsername("currentUsername").orElseThrow(() -> new RuntimeException("Owner not found"));
    }

    public DogService(DogProfileRepository dogRepo, OwnerRepository ownerRepo) {
        this.dogRepo = dogRepo;
        this.ownerRepo = ownerRepo;
    }

    // Получить всех собак
    public List<DogProfileDto> getAll() {
        return dogRepo.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    // Получить собаку по ID
    public DogProfileDto getById(Long id) {
        Dog dog = dogRepo.findById(id).orElseThrow();
        return mapToDto(dog);
    }

    // Создание новой собаки — владелец устанавливается автоматически
    public DogProfileDto create(CreateDogRequest request) {
        Owner current = getCurrentOwner();

        Dog dog = new Dog();
        dog.setName(request.name);
        dog.setDescription(request.description);
        dog.setPhotoUrl(request.photoUrl);
        dog.setStatus("дома");
        dog.setOwner(current);

        dog = dogRepo.save(dog);
        return mapToDto(dog);
    }

    // Обновление собаки — только если пользователь является владельцем
    public DogProfileDto update(Long id, UpdateDogRequest request) {
        Owner current = getCurrentOwner();
        Dog dog = dogRepo.findById(id).orElseThrow();

        if (!dog.getOwner().getId().equals(current.getId())) {
            throw new RuntimeException("Not your dog");
        }

        dog.setDescription(request.description);
        dog.setStatus(request.status);

        dog = dogRepo.save(dog);
        return mapToDto(dog);
    }

    // Удаление собаки — только если пользователь владелец
    public void delete(Long id) {
        Owner current = getCurrentOwner();
        Dog dog = dogRepo.findById(id).orElseThrow();

        if (!dog.getOwner().getId().equals(current.getId())) {
            throw new RuntimeException("Cannot delete: not your dog");
        }

        dogRepo.delete(dog);
    }

    // Преобразование сущности DogProfile в DTO


    private DogProfileDto mapToDto(Dog dog) {
        DogProfileDto dto = new DogProfileDto();
        dto.id = dog.getId();
        dto.name = dog.getName();
        dto.description = dog.getDescription();
        dto.photoUrl = dog.getPhotoUrl();
        dto.status = dog.getStatus();
        dto.ownerUsername = dog.getOwner().getUsername();
        dto.ownerAddress = dog.getOwner().getAddress();
        //dto.friendNames = dog.getFriends() != null ?
        //        dog.getFriends().stream().map(User::getUsername).toList() : List.of();
        return dto;
    }


    public void updatePhoto(Long id, String photoUrl) {
        Owner current = getCurrentOwner();
        Dog dog = dogRepo.findById(id).orElseThrow();
        if (!dog.getOwner().getId().equals(current.getId())) {
            throw new RuntimeException("Not your dog");
        }
        dog.setPhotoUrl(photoUrl);
        dogRepo.save(dog);
    }
}
