package repository;

import model.Reminder;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

/**
 * Interfejs repozytorium do zarządzania przypomnieniami w bazie danych MongoDB.
 * Dziedziczy po {@link MongoRepository}, umożliwiając podstawowe operacje CRUD na kolekcji "Reminder".
 */
public interface ReminderRepository extends MongoRepository<Reminder, String> {

    /**
     * Usuwa wszystkie przypomnienia, które zostały oznaczone jako zakończone.
     *
     * @return liczba usuniętych przypomnień
     */
    long deleteByIsCompletedTrue();

    /**
     * Wyszukuje wszystkie przypomnienia, które nie zostały jeszcze wysłane oraz mają datę wcześniejszą niż podana.
     *
     * @param date data, przed którą przypomnienia powinny zostać wysłane
     * @return lista przypomnień, które spełniają podane warunki
     */
    List<Reminder> findByIsSentFalseAndDateBefore(String date);
}
