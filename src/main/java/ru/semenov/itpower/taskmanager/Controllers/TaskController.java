package ru.semenov.itpower.taskmanager.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.semenov.itpower.taskmanager.Service.TaskService;
import ru.semenov.itpower.taskmanager.model.ApiResponse;
import ru.semenov.itpower.taskmanager.model.Task;

import java.util.Optional;

@Controller
@RequestMapping("/api/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @PostMapping("/{personId}/")
    public ResponseEntity<Task> createTask(@ModelAttribute Task task, @PathVariable Long personId) {
        return ResponseEntity.ok(taskService.createTask(task, personId));
    }
    @PutMapping("/update/{taskId}/")
    public ResponseEntity<Task> updateTask(@ModelAttribute Task task, @PathVariable Long taskId) {
        Optional<Task> response = taskService.updateTask(taskId, task);
        if(response.isPresent()) {
            return ResponseEntity.ok(response.get());
        } else {
            throw new RuntimeException("Задача не найдена");
        }
    }
    @GetMapping("/{personId}/tasks/")
    public ResponseEntity<Page<Task>> findAllByPersonEquals(@PathVariable Long personId,
                                                           @RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "20") int size) {
        Page<Task> tasks = taskService.findAllByPersonEquals(personId, page, size);
        return ResponseEntity.ok(tasks);
    }

    @DeleteMapping("/*/{taskId}/")
    public ApiResponse deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
        ApiResponse response = new ApiResponse();
        response.setMessage("Задача успешно удалена");
        response.setStatus(true);
        return response;
    }
}
