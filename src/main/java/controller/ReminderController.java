package controller;

import model.Reminder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.ReminderRepository;
import java.time.OffsetDateTime;
import java.util.List;

/**
 * Klasa kontrolera zarządzająca przypomnieniami. Udostępnia punkty końcowe do pobierania,
 * dodawania, aktualizowania, usuwania przypomnień, a także umożliwia oznaczanie przypomnień
 * jako wysłane i czyszczenie zakończonych przypomnień.
 */
@RestController
@RequestMapping("/api/reminders")
public class ReminderController {

    @Autowired
    private ReminderRepository reminderRepository;

    /**
     * Pobiera wszystkie przypomnienia.
     *
     * @return lista wszystkich przypomnień
     */
    @GetMapping
    public List<Reminder> getAllReminders() {
        return reminderRepository.findAll();
    }

    /**
     * Pobiera przypomnienie po jego ID.
     *
     * @param id ID przypomnienia
     * @return przypomnienie o podanym ID lub null, jeśli nie znaleziono
     */
    @GetMapping("/{id}")
    public Reminder getReminderById(@PathVariable String id) {
        return reminderRepository.findById(id).orElse(null);
    }

    /**
     * Pobiera przypomnienia, które nie zostały jeszcze wysłane i mają datę przed bieżącym czasem.
     *
     * @return lista przypomnień oczekujących na wysłanie
     */
    @GetMapping("/pending")
    public List<Reminder> getPendingReminders() {
        OffsetDateTime now = OffsetDateTime.now();
        return reminderRepository.findByIsSentFalseAndDateBefore(now.toString());
    }

    /**
     * Dodaje nowe przypomnienie. Jeśli tytuł lub data przypomnienia są puste,
     * zostaje zwrócony błąd 400 (zła prośba).
     *
     * @param reminder przypomnienie do dodania
     * @return ResponseEntity zawierające zapisane przypomnienie
     */
    @PostMapping
    public ResponseEntity<Reminder> addReminder(@RequestBody Reminder reminder) {
        if (reminder.getTitle() == null || reminder.getTitle().isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        if (reminder.getDate() == null) {
            return ResponseEntity.badRequest().body(null);
        }

        Reminder savedReminder = reminderRepository.save(reminder);
        return ResponseEntity.ok(savedReminder);
    }

    /**
     * Aktualizuje istniejące przypomnienie o podanym ID. Dane przypomnienia zostaną
     * zastąpione danymi z zapytania.
     *
     * @param id ID przypomnienia do aktualizacji
     * @param reminder zaktualizowane dane przypomnienia
     * @return zaktualizowane przypomnienie
     */
    @PutMapping("/{id}")
    public Reminder updateReminder(@PathVariable String id, @RequestBody Reminder reminder) {
        reminder.setId(id);
        return reminderRepository.save(reminder);
    }

    /**
     * Oznacza przypomnienie jako wysłane. Jeśli przypomnienie o podanym ID nie istnieje,
     * zwrócony zostanie błąd 404 (nie znaleziono).
     *
     * @param id ID przypomnienia do oznaczenia jako wysłane
     * @return ResponseEntity zawierające zaktualizowane przypomnienie
     */
    @PutMapping("/{id}/markSent")
    public ResponseEntity<Reminder> markReminderAsSent(@PathVariable String id) {
        Reminder reminder = reminderRepository.findById(id).orElse(null);
        if (reminder == null) {
            return ResponseEntity.notFound().build();
        }
        reminder.setSent(true);
        reminderRepository.save(reminder);
        return ResponseEntity.ok(reminder);
    }

    /**
     * Usuwa przypomnienie o podanym ID.
     *
     * @param id ID przypomnienia do usunięcia
     */
    @DeleteMapping("/{id}")
    public void deleteReminder(@PathVariable String id) {
        reminderRepository.deleteById(id);
    }

    /**
     * Usuwa przypomnienia, które zostały oznaczone jako zakończone.
     *
     * @return ResponseEntity z komunikatem informującym, ile przypomnień zostało usuniętych
     *         lub informacją, że nie znaleziono przypomnień do usunięcia
     */
    @DeleteMapping("/cleanup")
    public ResponseEntity<String> cleanupCompletedReminders() {
        long completedCount = reminderRepository.deleteByIsCompletedTrue();
        if (completedCount > 0) {
            return ResponseEntity.ok("Deleted " + completedCount + " completed reminders.");
        }
        return ResponseEntity.ok("No completed reminders to delete.");
    }
}
