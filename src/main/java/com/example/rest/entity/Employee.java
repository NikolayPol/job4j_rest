package com.example.rest.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Класс Employee
 *
 * @author Nikolay Polegaev
 * @version 1.0 19.12.2021
 */
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private int id;
    private String firstname;
    private String secondname;
    private int inn;
    @Column(name = "hire_date")
    private Timestamp hireDate;

    private List<Person> persons = new ArrayList<>();

    public static Employee of(int id, String firstname,
                       String secondname,
                       int inn,
                       List<Person> persons) {
        Employee employee = new Employee();
        employee.id = id;
        employee.firstname = firstname;
        employee.secondname = secondname;
        employee.inn = inn;
        employee.hireDate = Timestamp.valueOf(LocalDateTime.now());
        employee.persons = persons;
        return employee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Employee employee = (Employee) o;
        return id == employee.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
