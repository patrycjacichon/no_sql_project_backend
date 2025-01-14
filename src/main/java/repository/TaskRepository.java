package repository;

import model.Task; // klasa encji (modelu danych)
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TaskRepository extends MongoRepository<Task, String> {
    Task findByTitle(String title);
    List<Task> findByPriority(String priority);
    List<Task> findByStatus(String status);
    List<Task> findByTagsContaining(String tag);
}
