package com.example.rest.repository;

import com.example.rest.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Класс PersonRepository
 *
 * @author Nikolay Polegaev
 * @version 1.0 19.12.2021
 */
public interface PersonRepository extends JpaRepository<Person, Integer> {
}
