package repository;

import model.Reminder;
import model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReminderRepository extends MongoRepository<Reminder, String> {
    // dodatkowe metody zapytan
}
