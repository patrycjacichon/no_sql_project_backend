package model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.OffsetDateTime;

/**
 * Klasa reprezentująca przypomnienie. Obiekt tej klasy jest przechowywany w kolekcji "reminders" w bazie MongoDB.
 * Zawiera informacje o przypomnieniu, takie jak tytuł, opis, data, status ukończenia i status wysłania.
 */
@Document(collection = "reminders")
public class Reminder {

    @Id
    private String id;
    private String title;
    private String description;
    //@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    /**
     * Data przypomnienia w formacie String. Może być zmieniona na `OffsetDateTime` w przyszłości, aby lepiej obsługiwać daty i strefy czasowe.
     */
    private String date;
    private boolean isCompleted;
    private boolean isSent;

    /**
     * Konstruktor bezargumentowy.
     * Używany do tworzenia pustego obiektu przypomnienia.
     */
    public Reminder() {}

    /**
     * Konstruktor inicjalizujący wszystkie pola klasy.
     * Używany do tworzenia pełnego obiektu przypomnienia z wszystkimi jego właściwościami.
     *
     * @param title tytuł przypomnienia
     * @param description opis przypomnienia
     * @param date data przypomnienia w formacie String
     * @param isCompleted status ukończenia przypomnienia
     * @param isSent status wysłania przypomnienia
     */
    public Reminder(String title, String description, String date, boolean isCompleted, boolean isSent) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.isCompleted = isCompleted;
        this.isSent = isSent;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public boolean isSent() {
        return isSent;
    }

    public void setSent(boolean sent) {
        isSent = sent;
    }
}
