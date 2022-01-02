package swa.eshop.basket.model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.Map;

@Entity
@Table(name = "baskets")
@Setter
@Getter
public class Basket {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private Long userId;

    @ElementCollection
    @Column
    private Map<Long, Integer> items;

    public Basket() {}

    @Override
    public String toString() {
        return "Basket [ID: "+id+ ", belonging to User ID: " +userId+ ", containing: "+items.toString();
    }

}
