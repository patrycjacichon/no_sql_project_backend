package repository;

import model.ArchivedTask;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface ArchivedTaskRepository extends MongoRepository<ArchivedTask, String> {
    default List<ArchivedTask> findOlderThan24Hours(Date currentDate) {
        long twentyFourHoursInMillis = 24 * 60 * 60 * 1000; // 24 godziny w milisekundach
        Date twentyFourHoursBefore = new Date(currentDate.getTime() - twentyFourHoursInMillis);
        return findByDeleteTimeBefore(twentyFourHoursBefore);
    }
    List<ArchivedTask> findByDeleteTimeBefore(Date date);
}
