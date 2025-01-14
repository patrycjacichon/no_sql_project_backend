package repository;

import model.Reminder;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReminderRepository extends MongoRepository<Reminder, String> {
    long deleteByIsCompletedTrue();
}
