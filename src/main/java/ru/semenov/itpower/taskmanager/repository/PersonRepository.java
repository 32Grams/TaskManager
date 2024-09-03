package ru.semenov.itpower.taskmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.semenov.itpower.taskmanager.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
}
