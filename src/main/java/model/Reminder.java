package model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "reminders")
public class Reminder {

    @Id
    private String id;
    private String title;
    private String description;
    private Date date;
    private boolean isCompleted;
    private boolean isSent;

    // konstruktor bez arg
    public Reminder() {}

    public Reminder(String title, String description, Date date, boolean isCompleted, boolean isSent) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.isCompleted = isCompleted;
        this.isSent = isSent;
    }

    // Gettery i Settery
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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
