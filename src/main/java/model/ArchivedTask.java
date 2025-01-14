package model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;
import java.util.Date;

@Document(collection = "Archivedtasks")
public class ArchivedTask {

    @Id
    private String id;
    private String title;
    private String priority;
    private String status;
    private List<String> tags;
    private Date deleteTime;

    // konstruktor bez arg
    public ArchivedTask() {}

    public ArchivedTask(String title, String priority, String status, List<String> tags, Date deleteTime) {
        this.title = title;
        this.priority = priority;
        this.status = status;
        this.tags = tags;
        this.deleteTime = deleteTime;
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

    public Date getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }
}
