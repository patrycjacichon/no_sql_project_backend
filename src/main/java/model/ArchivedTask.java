package model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;
import java.util.Date;

/**
 * Klasa reprezentująca archiwalne zadanie. Obiekt tej klasy jest przechowywany w kolekcji "Archivedtasks" w bazie MongoDB.
 * Zawiera informacje o zadaniu, takie jak tytuł, priorytet, status, tagi oraz czas usunięcia.
 */
@Document(collection = "Archivedtasks")
public class ArchivedTask {

    @Id
    private String id;
    private String title;
    private String priority;
    private String status;
    private List<String> tags;
    private Date deleteTime;

    /**
     * Konstruktor bezargumentowy.
     * Używany do tworzenia pustego obiektu zadania archiwalnego.
     */
    public ArchivedTask() {}

    /**
     * Konstruktor inicjalizujący wszystkie pola klasy.
     * Używany do tworzenia pełnego obiektu zadania archiwalnego z wszystkimi jego właściwościami.
     *
     * @param title tytuł zadania
     * @param priority priorytet zadania
     * @param status status zadania
     * @param tags lista tagów przypisanych do zadania
     * @param deleteTime czas usunięcia zadania
     */
    public ArchivedTask(String title, String priority, String status, List<String> tags, Date deleteTime) {
        this.title = title;
        this.priority = priority;
        this.status = status;
        this.tags = tags;
        this.deleteTime = deleteTime;
    }

    // Gettery i Settery
    /**
     * Pobiera identyfikator zadania.
     *
     * @return identyfikator zadania
     */
    public String getId() {
        return id;
    }

    /**
     * Ustawia identyfikator zadania.
     *
     * @param id identyfikator zadania
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Pobiera tytuł zadania.
     *
     * @return tytuł zadania
     */
    public String getTitle() {
        return title;
    }

    /**
     * Ustawia tytuł zadania.
     *
     * @param title tytuł zadania
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Pobiera priorytet zadania.
     *
     * @return priorytet zadania
     */
    public String getPriority() {
        return priority;
    }

    /**
     * Ustawia priorytet zadania.
     *
     * @param priority priorytet zadania
     */
    public void setPriority(String priority) {
        this.priority = priority;
    }

    /**
     * Pobiera status zadania.
     *
     * @return status zadania
     */
    public String getStatus() {
        return status;
    }

    /**
     * Ustawia status zadania.
     *
     * @param status status zadania
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Pobiera listę tagów przypisanych do zadania.
     *
     * @return lista tagów zadania
     */
    public List<String> getTags() {
        return tags;
    }

    /**
     * Ustawia listę tagów przypisanych do zadania.
     *
     * @param tags lista tagów zadania
     */
    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    /**
     * Pobiera czas usunięcia zadania.
     *
     * @return czas usunięcia zadania
     */
    public Date getDeleteTime() {
        return deleteTime;
    }

    /**
     * Ustawia czas usunięcia zadania.
     *
     * @param deleteTime czas usunięcia zadania
     */
    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }
}
