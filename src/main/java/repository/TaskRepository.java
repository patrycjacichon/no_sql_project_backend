package repository;

import model.Task; // klasa encji (modelu danych)
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TaskRepository extends MongoRepository<Task, String> {
    // dodatkowe metody zapytan
    Task findByName(String name);
}
