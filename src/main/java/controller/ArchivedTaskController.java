package controller;

import model.ArchivedTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.ArchivedTaskRepository;

import java.util.Date;
import java.util.List;

/**
 * Klasa kontrolera zarządzająca archiwalnymi zadaniami. Udostępnia punkty końcowe do pobierania,
 * dodawania, aktualizowania i usuwania archiwalnych zadań. Zawiera również funkcję czyszczenia
 * do usuwania zadań starszych niż 24 godziny.
 */
@RestController
@RequestMapping("/api/archivedtasks")
public class ArchivedTaskController {

    @Autowired
    private ArchivedTaskRepository archivedTaskRepository;

    /**
     * Pobiera wszystkie archiwalne zadania.
     *
     * @return lista wszystkich archiwalnych zadań
     */
    @GetMapping
    public List<ArchivedTask> getAllArchivedTasks() {
        return archivedTaskRepository.findAll();
    }

    /**
     * Pobiera archiwalne zadanie po jego ID.
     *
     * @param id ID archiwalnego zadania
     * @return archiwalne zadanie o podanym ID lub null, jeśli nie znaleziono
     */
    @GetMapping("/{id}")
    public ArchivedTask getArchivedTaskById(@PathVariable String id) {
        return archivedTaskRepository.findById(id).orElse(null);
    }

    /**
     * Dodaje nowe archiwalne zadanie do repozytorium. Jeśli tytuł jest pusty,
     * zostaje zwrócony błąd 400 (zła prośba). Jeśli deleteTime nie jest podane,
     * ustawiane jest na bieżący czas.
     *
     * @param archivedTask archiwalne zadanie do dodania
     * @return ResponseEntity zawierające zapisane archiwalne zadanie
     */
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

    /**
     * Aktualizuje istniejące archiwalne zadanie o podanym ID. Dane zadania zostaną
     * zastąpione danymi z zapytania.
     *
     * @param id ID archiwalnego zadania do aktualizacji
     * @param archivedTask zaktualizowane dane zadania
     * @return zaktualizowane archiwalne zadanie
     */
    @PutMapping("/{id}")
    public ArchivedTask updateArchivedTask(@PathVariable String id, @RequestBody ArchivedTask archivedTask) {
        archivedTask.setId(id);
        return archivedTaskRepository.save(archivedTask);
    }

    /**
     * Usuwa archiwalne zadanie o podanym ID.
     *
     * @param id ID archiwalnego zadania do usunięcia
     */
    @DeleteMapping("/{id}")
    public void deleteArchivedTask(@PathVariable String id) {
        archivedTaskRepository.deleteById(id);
    }

    /**
     * Usuwa archiwalne zadania starsze niż 24 godziny. Metoda ta sprawdza zadania starsze
     * niż 24 godziny na podstawie ich deleteTime i usuwa je z repozytorium.
     *
     * @return ResponseEntity z komunikatem informującym, ile zadań zostało usuniętych
     *         lub informacją, że nie znaleziono zadań starszych niż 24 godziny
     */
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
