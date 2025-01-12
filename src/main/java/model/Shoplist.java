package model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "shoplist")
public class Shoplist {

    @Id
    private String id;
    private String item;
    private String category;
    private Integer quantity;
    private boolean purchased;

    // konstruktor bez arg
    public Shoplist() {}

    public Shoplist(String item, String category, Integer quantity, boolean purchased) {
        this.item = item;
        this.category = category;
        this.quantity = quantity;
        this.purchased = purchased;
    }

    // Gettery i Settery
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getQuantity() {
        return quantity != null ? quantity : 0;  // zwraca 0 jeskli quantity jest null
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isPurchased() {
        return purchased;
    }

    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }
}
