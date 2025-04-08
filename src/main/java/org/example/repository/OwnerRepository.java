package org.example.repository;

import org.example.model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
    // Поиск владельца по имени
    Optional<Owner> findByFirstNameAndLastName(String firstName, String lastName);

    // Пример запроса для получения всех владельцев по городу
    List<Owner> findByCity(String city);

    // Пример запроса для получения всех владельцев по имени пользователя
    Optional<Owner> findByUsername(String username);
}
