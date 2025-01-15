package controller;

import model.Reminder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.ReminderRepository;
import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/reminders")
public class ReminderController {

    @Autowired
    private ReminderRepository reminderRepository;

    @GetMapping
    public List<Reminder> getAllReminders() {
        return reminderRepository.findAll();
    }

    @GetMapping("/{id}")
    public Reminder getReminderById(@PathVariable String id) {
        return reminderRepository.findById(id).orElse(null);
    }

    @GetMapping("/pending")
    public List<Reminder> getPendingReminders() {
        OffsetDateTime now = OffsetDateTime.now();
        return reminderRepository.findByIsSentFalseAndDateBefore(now.toString());
    }

    // powiadomienia
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

    @PutMapping("/{id}")
    public Reminder updateReminder(@PathVariable String id, @RequestBody Reminder reminder) {
        reminder.setId(id);
        return reminderRepository.save(reminder);
    }

    // sprawdzanie powiadomien czy wyslane
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

    @DeleteMapping("/{id}")
    public void deleteReminder(@PathVariable String id) {
        reminderRepository.deleteById(id);
    }

    // usuwanie powiadomien zakonczonych
    @DeleteMapping("/cleanup")
    public ResponseEntity<String> cleanupCompletedReminders() {
        long completedCount = reminderRepository.deleteByIsCompletedTrue();
        if (completedCount > 0) {
            return ResponseEntity.ok("Deleted " + completedCount + " completed reminders.");
        }
        return ResponseEntity.ok("No completed reminders to delete.");
    }
}
