package repository;

import model.Reminder;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface ReminderRepository extends MongoRepository<Reminder, String> {
    long deleteByIsCompletedTrue();
    List<Reminder> findByIsSentFalseAndDateBefore(String date);
}
