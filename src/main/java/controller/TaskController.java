package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import model.Task;
import repository.TaskRepository;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable String id) {
        return taskRepository.findById(id).orElse(null);
    }

    @PostMapping
    public ResponseEntity<Task> addTask(@RequestBody Task task) {
        if (task.getName() == null || task.getName().isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        if (task.getDescription() == null || task.getDescription().isEmpty()) {
            task.setDescription("brak");  // Domyślna wartosc
        }

        // Zapisz zadanie
        Task savedTask = taskRepository.save(task);
        return ResponseEntity.ok(savedTask);
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable String id, @RequestBody Task task) {
        task.setId(id);
        return taskRepository.save(task);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable String id) {
        taskRepository.deleteById(id);
    }
}
