package ru.semenov.itpower.taskmanager.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.semenov.itpower.taskmanager.Service.PersonService;
import ru.semenov.itpower.taskmanager.model.Person;

import java.util.List;

@Controller
@RequestMapping("/api/persons")
public class PersonController {
    @Autowired
    private PersonService personService;

    @GetMapping("/")
    private ResponseEntity<List<Person>> getAllPersons() {
        return ResponseEntity.ok(personService.getAllPersons());
    }

    @PostMapping("/")
    public Person savePerson(@ModelAttribute Person person) {
        return personService.savePerson(person);
    }
}
