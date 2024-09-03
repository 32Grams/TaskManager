package ru.semenov.itpower.taskmanager.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.semenov.itpower.taskmanager.model.Person;
import ru.semenov.itpower.taskmanager.repository.PersonRepository;

import java.util.List;


@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepository;

    public Person savePerson(Person person) {
        return personRepository.save(person);
    }

    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }
    public Person getPersonById(Long id) {
        return personRepository.getReferenceById(id);
    }

}
