package com.example.rest.control;

import com.example.rest.Job4jRestApplication;
import com.example.rest.entity.Person;
import com.example.rest.repository.PersonRepository;
import com.example.rest.service.PersonService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Класс PersonControllerTest
 * Класс с тестами
 *
 * @author Nikolay Polegaev
 * @version 1.0 19.12.2021
 */
@SpringBootTest(classes = Job4jRestApplication.class)
@AutoConfigureMockMvc
class PersonControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    @Test
    public void findAllTest() throws Exception {
        List<Person> persons = new ArrayList<>();
        persons.add(Person.of(1, "Parfiry", "123"));
        persons.add(Person.of(2, "Vsevolod", "456"));

        when(personService.findAll()).thenReturn(persons);

        String expected = "[{\"id\":1,\"login\":\"Parfiry\",\"password\":\"123\"},"
                + "{\"id\":2,\"login\":\"Vsevolod\",\"password\":\"456\"}]";
        this.mockMvc.perform(get("/person/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(expected)));
    }

    @Test
    public void findByIdTest() throws Exception {
        Person person = Person.of(1, "Parfiry", "123");

        when(personService.findById(1)).thenReturn(Optional.of(person));

        String expected = "{\"id\":1,\"login\":\"Parfiry\",\"password\":\"123\"}";

        this.mockMvc.perform(get("/person/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(expected)));
    }

    @Test
    public void createTest() throws Exception {
        Person person = Person.of(1, "Parfiry", "123");
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String personJson = gson.toJson(person);

        when(personService.save(person)).thenReturn(person);

        String expected = "{\"id\":1,\"login\":\"Parfiry\",\"password\":\"123\"}";

        this.mockMvc.perform(post("/person/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(personJson))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString(expected)));
    }

    @Test
    public void updateTest() throws Exception {
        Person person = Person.of(1, "Parfiry", "123");
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String personJson = gson.toJson(person);

        this.mockMvc.perform(put("/person/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(personJson))
                .andDo(print())
                .andExpect(status().isOk());
        verify(personService, times(1)).update(person);
    }

    @Test
    public void deleteTest() throws Exception {
        this.mockMvc.perform(delete("/person/1"))
                .andDo(print())
                .andExpect(status().isOk());
        verify(personService, times(1)).delete(anyInt());
    }

}