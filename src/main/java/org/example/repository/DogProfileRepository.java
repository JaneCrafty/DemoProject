package org.example.repository;


import org.example.model.Dog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DogProfileRepository extends JpaRepository<Dog, Long> {
    // Поиск собаки по имени
    Optional<Dog> findByName(String name);

    // Поиск собаки по владельцу
    List<Dog> findByOwnerId(Long ownerId);

    // Пример пользовательского запроса
    List<Dog> findByBreed(String breed);
}
