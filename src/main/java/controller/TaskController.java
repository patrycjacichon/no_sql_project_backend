package controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import model.Task;
import repositories.TaskRepository;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    // pobranie wszystkich zadan
    @GetMapping
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // pobranie zadan po id
    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable String id) {
        return taskRepository.findById(id).orElse(null);
    }

    // dodanie nowych zadan
    @PostMapping
    public Task addTask(@RequestBody Task task) {
        return taskRepository.save(task);
    }

    // aktualizacja zadania
    @PutMapping("/{id}")
    public Task updateTask(@PathVariable String id, @RequestBody Task task) {
        task.setId(id);
        return taskRepository.save(task);
    }

    // usuniecie zadania
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable String id) {
        taskRepository.deleteById(id);
    }
}
