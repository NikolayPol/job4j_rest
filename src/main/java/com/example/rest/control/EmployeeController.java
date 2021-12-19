package com.example.rest.control;

import com.example.rest.entity.Employee;
import com.example.rest.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс EmployeeController - использует RestTemplate
 *
 * @author Nikolay Polegaev
 * @version 1.0 19.12.2021
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private static final String API = "http://localhost:8080/person/";

    private static final String API_ID = "http://localhost:8080/person/{id}";

    @Autowired
    private RestTemplate rest;

    private List<Employee> emps = List.of(
            Employee.of(1, "Parfiry", "Parfiriev", 770011, new ArrayList<>()),
            Employee.of(2, "Vsevolod", "Vs", 770022, new ArrayList<>())
    );

    @GetMapping("/")
    public List<Employee> findAll() {
        List<Employee> rsl = new ArrayList<>();
        List<Person> persons = rest.exchange(
                API,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Person>>() {
                }
        ).getBody();

        for (Person person : persons) {
            emps.get(person.getId() - 1).getPersons().add(person);
        }
        return emps;
    }

    @PostMapping("/")
    public ResponseEntity<Employee> create(@RequestBody Employee employee) {
        Employee rsl = rest.postForObject(API, employee, Employee.class);
        return new ResponseEntity<Employee>(
                rsl,
                HttpStatus.CREATED
        );
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Employee employee) {
        rest.put(API, employee);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        rest.delete(API_ID, id);
        return ResponseEntity.ok().build();
    }

}
