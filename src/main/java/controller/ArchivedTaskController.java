package controller;

import model.ArchivedTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.ArchivedTaskRepository;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/archivedtasks")
public class ArchivedTaskController {

    @Autowired
    private ArchivedTaskRepository archivedTaskRepository;

    @GetMapping
    public List<ArchivedTask> getAllArchivedTasks() {
        return archivedTaskRepository.findAll();
    }

    // Pobranie zadania po ID
    @GetMapping("/{id}")
    public ArchivedTask getArchivedTaskById(@PathVariable String id) {
        return archivedTaskRepository.findById(id).orElse(null);
    }

    @PostMapping
    public ResponseEntity<ArchivedTask> addArchivedTask(@RequestBody ArchivedTask archivedTask) {
        if (archivedTask.getTitle() == null || archivedTask.getTitle().isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        if (archivedTask.getDeleteTime() == null) {
            archivedTask.setDeleteTime(new Date());
        }

        // Zapisanie zadania
        ArchivedTask savedArchivedTask = archivedTaskRepository.save(archivedTask);
        return ResponseEntity.ok(savedArchivedTask);
    }

    @PutMapping("/{id}")
    public ArchivedTask updateArchivedTask(@PathVariable String id, @RequestBody ArchivedTask archivedTask) {
        archivedTask.setId(id);
        return archivedTaskRepository.save(archivedTask);
    }

    @DeleteMapping("/{id}")
    public void deleteArchivedTask(@PathVariable String id) {
        archivedTaskRepository.deleteById(id);
    }

    // Usuwanie archiwalnych taskow 24 godziny po deleteTime
    @DeleteMapping("/cleanup")
    public ResponseEntity<String> cleanupOldArchivedTasks() {
        Date currentTime = new Date();
        List<ArchivedTask> tasksToDelete = archivedTaskRepository.findOlderThan24Hours(currentTime);

        if (!tasksToDelete.isEmpty()) {
            archivedTaskRepository.deleteAll(tasksToDelete);
            return ResponseEntity.ok("Deleted " + tasksToDelete.size() + " archived tasks.");
        }

        return ResponseEntity.ok("No tasks older than 24 hours to delete.");
    }
}
