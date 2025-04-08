package org.example.controller;

import org.example.dto.CreateDogRequest;
import org.example.dto.DogProfileDto;
import org.example.dto.UpdateDogRequest;
import org.example.service.DogService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/dogs")
public class DogController {
    private final DogService dogService;

    public DogController(DogService dogService) {
        this.dogService = dogService;
    }

    @GetMapping
    public List<DogProfileDto> getAll() {
        return dogService.getAll();
    }

    @GetMapping("/{id}")
    public DogProfileDto get(@PathVariable Long id) {
        return dogService.getById(id);
    }

    @PostMapping
    public DogProfileDto create(@RequestBody CreateDogRequest request) {
        return dogService.create(request);
    }

    @PutMapping("/{id}")
    public DogProfileDto update(@PathVariable Long id, @RequestBody UpdateDogRequest request) {
        return dogService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        dogService.delete(id);
    }

    @PostMapping("/{id}/photo")
    public void uploadPhoto(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws IOException {
        String uploadDir = "uploads/";
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();

        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        File destination = new File(uploadDir + filename);
        file.transferTo(destination);

        dogService.updatePhoto(id, "/uploads/" + filename);
    }
}

