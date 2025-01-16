package controller;

import model.ArchivedTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Date;
import model.Task;
import repository.ArchivedTaskRepository;
import repository.TaskRepository;

/**
 * Klasa kontrolera, która zarządza zadaniami. Umożliwia wykonywanie operacji takich jak:
 * - Pobieranie wszystkich zadań
 * - Pobieranie zadania po ID, tytule, priorytecie, statusie lub tagu
 * - Dodawanie nowych zadań
 * - Aktualizowanie istniejących zadań
 * - Usuwanie zadań
 * - Archiwizowanie zakończonych zadań
 * - Automatyczne archiwizowanie zadań zakończonych codziennie o 2:00 w nocy
 */
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ArchivedTaskRepository archivedTaskRepository;

    /**
     * Pobiera wszystkie zadania.
     *
     * @return lista wszystkich zadań
     */
    @GetMapping
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    /**
     * Pobiera zadanie na podstawie jego ID.
     *
     * @param id ID zadania
     * @return ResponseEntity zawierające zadanie o podanym ID lub błąd 404, jeśli zadanie nie istnieje
     */
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable String id) {
        Task task = taskRepository.findById(id).orElse(null);
        if (task != null) {
            return ResponseEntity.ok(task);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Dodaje nowe zadanie. Jeśli tytuł lub inne wymagane dane są puste,
     * zwrócony zostanie błąd 400 (zła prośba).
     *
     * @param task zadanie do dodania
     * @return ResponseEntity zawierające zapisane zadanie
     */
    @PostMapping
    public ResponseEntity<Task> addTask(@RequestBody Task task) {
        if (task.getTitle() == null || task.getTitle().isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        // Ustawienie domyslnych wartosci
        if (task.getPriority() == null || task.getPriority().isEmpty()) {
            task.setPriority("low");
        }
        if (task.getStatus() == null || task.getStatus().isEmpty()) {
            task.setStatus("pending");
        }
        if (task.getTags() == null) {
            task.setTags(List.of());
        }

        // Zapisanie zadania
        Task savedTask = taskRepository.save(task);
        return ResponseEntity.ok(savedTask);
    }

    /**
     * Aktualizuje zadanie o podanym ID.
     *
     * @param id ID zadania do aktualizacji
     * @param task zaktualizowane dane zadania
     * @return ResponseEntity z zaktualizowanym zadaniem lub błąd 404, jeśli zadanie nie istnieje
     */
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable String id, @RequestBody Task task) {
        if (!taskRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        task.setId(id); // Ustawienie ID na podstawie sciezki
        Task updatedTask = taskRepository.save(task);
        return ResponseEntity.ok(updatedTask);
    }

    /**
     * Usuwa zadanie o podanym ID.
     *
     * @param id ID zadania do usunięcia
     * @return ResponseEntity potwierdzające usunięcie zadania lub błąd 404, jeśli zadanie nie istnieje
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable String id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Pobiera zadanie na podstawie tytułu.
     *
     * @param title tytuł zadania
     * @return ResponseEntity zawierające zadanie o podanym tytule lub błąd 404, jeśli zadanie nie istnieje
     */
    @GetMapping("/title/{title}")
    public ResponseEntity<Task> getTaskByTitle(@PathVariable String title) {
        Task task = taskRepository.findByTitle(title);
        if (task != null) {
            return ResponseEntity.ok(task);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Pobiera zadania o określonym priorytecie.
     *
     * @param priority priorytet zadania
     * @return lista zadań o podanym priorytecie
     */
    @GetMapping("/priority/{priority}")
    public List<Task> getTasksByPriority(@PathVariable String priority) {
        return taskRepository.findByPriority(priority);
    }

    /**
     * Pobiera zadania o określonym statusie.
     *
     * @param status status zadania
     * @return lista zadań o podanym statusie
     */
    @GetMapping("/status/{status}")
    public List<Task> getTasksByStatus(@PathVariable String status) {
        return taskRepository.findByStatus(status);
    }

    /**
     * Pobiera zadania zawierające określony tag.
     *
     * @param tag tag zadania
     * @return lista zadań zawierających podany tag
     */
    @GetMapping("/tags/{tag}")
    public List<Task> getTasksByTag(@PathVariable String tag) {
        return taskRepository.findByTagsContaining(tag);
    }

    /**
     * Archiwizuje zadanie o podanym ID. Zadanie zostaje zapisane w archiwum, a następnie usunięte z aktywnych zadań.
     *
     * @param id ID zadania do archiwizacji
     * @return ResponseEntity zawierające komunikat o sukcesie
     */
    @PostMapping("/{id}/archive")
    public ResponseEntity<String> archiveTask(@PathVariable String id) {
        // pobranie zadania
        Task task = taskRepository.findById(id).orElse(null);
        if (task == null) {
            return ResponseEntity.notFound().build();
        }

        // tworzenie nowego task do archiwum
        ArchivedTask archivedTask = new ArchivedTask(
                task.getTitle(),
                task.getPriority(),
                task.getStatus(),
                task.getTags(),
                new Date() // Czas archiwizacji
        );

        archivedTaskRepository.save(archivedTask);

        taskRepository.deleteById(id);

        return ResponseEntity.ok("Task archived and deleted successfully.");
    }

    /**
     * Zadanie zaplanowane na codzienne archiwizowanie zadań zakończonych o 2:00 w nocy.
     * Zadania o statusie "completed" zostaną przeniesione do archiwum i usunięte z aktywnych zadań.
     */
    @Scheduled(cron = "0 0 2 * * ?") // Codziennie o 2:00 w nocy
    public void archiveCompletedTasks() {
        List<Task> completedTasks = taskRepository.findByStatus("completed");

        for (Task task : completedTasks) {
            ArchivedTask archivedTask = new ArchivedTask(
                    task.getTitle(),
                    task.getPriority(),
                    task.getStatus(),
                    task.getTags(),
                    new Date() // Aktualny czas jako czas archiwizacji
            );

            archivedTaskRepository.save(archivedTask);
            taskRepository.deleteById(task.getId());
        }
    }
}
