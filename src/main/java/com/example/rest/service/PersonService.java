package com.example.rest.service;

import com.example.rest.entity.Person;
import com.example.rest.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Класс PersonService
 *
 * @author Nikolay Polegaev
 * @version 1.0 19.12.2021
 */
@Service
@AllArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Optional<Person> findById(int id) {
        return personRepository.findById(id);
    }

    public Person save(Person person) {
        return personRepository.save(person);
    }

    public void update(Person person) {
        Optional<Person> personFromDB = personRepository.findById(person.getId());
        if (personFromDB.isPresent()) {
            person.setId(personFromDB.get().getId());
            personRepository.save(person);
        } else {
            personRepository.save(person);
        }
    }

    public void delete(int id) {
        personRepository.delete(personRepository.findById(id).get());
    }
}
