package repository;

import model.ArchivedTask;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

/**
 * Interfejs repozytorium do zarządzania archiwalnymi zadaniami w bazie danych MongoDB.
 * Dziedziczy po {@link MongoRepository}, umożliwiając podstawowe operacje CRUD na kolekcji "ArchivedTask".
 * Dodatkowo zawiera metodę do wyszukiwania zadań, które zostały usunięte ponad 24 godziny temu.
 */
public interface ArchivedTaskRepository extends MongoRepository<ArchivedTask, String> {

    /**
     * Wyszukuje wszystkie zadania, które zostały usunięte ponad 24 godziny temu.
     * Metoda oblicza czas sprzed 24 godzin i wykorzystuje go do znalezienia odpowiednich zadań.
     *
     * @param currentDate bieżąca data, na podstawie której obliczany jest czas sprzed 24 godzin
     * @return lista zadań, które zostały usunięte ponad 24 godziny temu
     */
    default List<ArchivedTask> findOlderThan24Hours(Date currentDate) {
        long twentyFourHoursInMillis = 24 * 60 * 60 * 1000; // 24 godziny w milisekundach
        Date twentyFourHoursBefore = new Date(currentDate.getTime() - twentyFourHoursInMillis);
        return findByDeleteTimeBefore(twentyFourHoursBefore);
    }

    /**
     * Wyszukuje wszystkie zadania, które zostały usunięte przed określoną datą.
     *
     * @param date data, przed którą zadania zostały usunięte
     * @return lista zadań, które zostały usunięte przed podaną datą
     */
    List<ArchivedTask> findByDeleteTimeBefore(Date date);
}
