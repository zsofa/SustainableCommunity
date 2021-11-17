package Progmatic.SustainableCommunity.models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;
    @Column(nullable = false)
    private String itemName;
    @Enumerated(EnumType.STRING)
    private ItemCategory itemCategory;
    @Enumerated(EnumType.STRING)
    private ItemCondition itemCondition;
    private Integer itemValue;
    private Integer borrowPrice;
    private boolean isAvailable;
    @CreationTimestamp
    private LocalDateTime upload;
    @Column(nullable = false)
    private Double itemHeight;
    @Column(nullable = false)
    private Double itemWidth;
    // need owner


    public Item() {
    }

    public Item(String itemName,
                ItemCategory itemCategory,
                ItemCondition itemCondition,
                Integer itemValue,
                Integer borrowPrice) {
        this.itemName = itemName;
        this.itemCategory = itemCategory;
        this.itemCondition = itemCondition;
        this.itemValue = itemValue;
        this.borrowPrice = borrowPrice;
    }
}
