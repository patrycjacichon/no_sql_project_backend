package model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Klasa reprezentująca zadanie. Obiekt tej klasy jest przechowywany w kolekcji "tasks" w bazie MongoDB.
 * Zawiera informacje o zadaniu, takie jak tytuł, priorytet, status oraz tagi.
 */
@Document(collection = "tasks")
public class Task {

    @Id
    private String id;
    private String title;
    private String priority;
    private String status;
    private List<String> tags;

    /**
     * Konstruktor bezargumentowy.
     * Używany do tworzenia pustego obiektu zadania.
     */
    public Task() {
    }

    /**
     * Konstruktor inicjalizujący tytuł, priorytet oraz tagi zadania.
     * Używany do tworzenia obiektu zadania z określonymi wartościami.
     *
     * @param title tytuł zadania
     * @param priority priorytet zadania
     * @param tags lista tagów przypisanych do zadania
     */
    public Task(String title, String priority, List<String> tags) {
        this.title = title;
        this.priority = priority;
        this.tags = tags;
    }

    /**
     * Gettery i Settery
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
