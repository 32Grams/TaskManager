package ru.semenov.itpower.taskmanager.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.semenov.itpower.taskmanager.model.Person;
import ru.semenov.itpower.taskmanager.model.Task;
import ru.semenov.itpower.taskmanager.repository.TaskRepository;

import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private PersonService personService;

    public Task createTask(Task task, Long personId) {
        Person person = personService.getPersonById(personId);
        if(person != null) {
            task.setPerson(person);
            return taskRepository.save(task);
        } else {
            throw new RuntimeException("Такого пользователя не существует");
        }
    }

    public Optional<Task> updateTask(Long taskId, Task updatedTask) {
        return taskRepository.findById(taskId)
                .map(task -> {
                    task.setTitle(updatedTask.getTitle());
                    task.setDate(updatedTask.getDate());
                    task.setDescription(updatedTask.getDescription());
                    task.setPerson(updatedTask.getPerson());
                    return taskRepository.save(task);
                });
    }

    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    public Page<Task> findAllByPersonEquals(Long personId, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return taskRepository.findAllByPersonIdEquals(personId, pageable);
    }

}
