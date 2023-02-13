package org.task.hr.controller;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.task.hr.entity.Person;
import org.task.hr.repository.PersonRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/hrapi")
public class HrApi {
    @Autowired
    private PersonRepository personRepository;

    @GetMapping(path = "/persons", produces = "application/json")
    public List<Person> getPersons() {
        return personRepository.findAll();
    }

    @GetMapping(path = "/persons/{id}", produces = "application/json")
    public Person getOne(@PathVariable("id") Long id) {
        Optional<Person> oPerson = personRepository.findById(id);
        if (oPerson.isPresent()) {
            return oPerson.get();
        }
        return null;
    }

    @PutMapping(path = "/persons/{id}", produces = "application/json")
    @Transactional
    //  @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("id") Long id, @RequestBody Person person) {
        Optional<Person> oPerson = personRepository.findById(id);
        if (oPerson.isPresent()) {
            person.setId(id);
            personRepository.save(person);
        }
    }

    @PostMapping(path = "/persons", produces = "application/json")
    @Transactional
    public Person add(@RequestBody Person person) {
        if (person != null) {
            personRepository.save(person);
            return person;
        } else {
            return null;
        }
    }

    @DeleteMapping(path = "/persons/{id}", produces = "application/json")
    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        Optional<Person> oPerson = personRepository.findById(id);
        if (oPerson.isPresent()) {
            personRepository.delete(oPerson.get());
        }
    }

    @GetMapping(path = "/", produces = "application/json")
    public String getHelp() {
        String help = "HELPAS ...\n" +
                "/hrapi/persons      - GET  : get list\n" +
                "/hrapi/persons/{id} - GET : get by id \n" +
                "/hrapi/persons      - POST : add \n" +
                "/hrapi/persons/{id} - PUT : update\n" +
                "/hrapi/persons/[id] - DELETE : delete\n";
        return help;
    }

}
