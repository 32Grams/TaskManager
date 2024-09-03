package ru.semenov.itpower.taskmanager.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.semenov.itpower.taskmanager.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Page<Task> findAll(Pageable pageable);
    Page<Task> findAllByPersonIdEquals(Long personId, Pageable pageable);
}
