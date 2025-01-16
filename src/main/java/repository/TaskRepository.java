package repository;

import model.Task; // klasa encji (modelu danych)
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Interfejs repozytorium do zarządzania zadaniami w bazie danych MongoDB.
 * Dziedziczy po {@link MongoRepository}, umożliwiając podstawowe operacje CRUD na kolekcji "Task".
 */
public interface TaskRepository extends MongoRepository<Task, String> {
    /**
     * Wyszukuje zadanie po jego tytule.
     *
     * @param title tytuł zadania
     * @return zadanie o podanym tytule
     */
    Task findByTitle(String title);

    /**
     * Wyszukuje wszystkie zadania o określonym priorytecie.
     *
     * @param priority priorytet zadania
     * @return lista zadań o podanym priorytecie
     */
    List<Task> findByPriority(String priority);

    /**
     * Wyszukuje wszystkie zadania o określonym statusie.
     *
     * @param status status zadania, którego szukamy (np. "pending", "completed")
     * @return lista zadań o podanym statusie
     */
    List<Task> findByStatus(String status);

    /**
     * Wyszukuje wszystkie zadania zawierające określony tag.
     *
     * @param tag tag, którego szukamy w zadaniach
     * @return lista zadań, które zawierają podany tag
     */
    List<Task> findByTagsContaining(String tag);
}
