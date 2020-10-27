package newmcdonaldapp;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="Menu_table")
public class Menu {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    String burgerName;
    int price;
    int stock;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBurgerName() {
        return burgerName;
    }

    public void setBurgerName(String burgerName) {
        this.burgerName = burgerName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
