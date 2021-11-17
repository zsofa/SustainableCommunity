package Progmatic.SustainableCommunity.models;

import Progmatic.SustainableCommunity.forms.ItemForm;
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
    @Column(length = 1200)
    private String description;
    @Column(nullable = false)
    /**
     * Height, Width: cm-ben adja meg a User.
     */
    private Double itemHeight;
    @Column(nullable = false)
    private Double itemWidth;
    private boolean isAvailable;
    @CreationTimestamp
    private LocalDateTime upload;
    @ManyToOne
    private AppUser owner;


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

    public Item(ItemForm itemForm){
        this(itemForm.getItemName(),
                itemForm.getItemCategory(),
                itemForm.getItemCondition(),
                itemForm.getItemValue(),
                itemForm.getBorrowPrice(),
                itemForm.getDescription(),
                itemForm.getItemHeight(),
                itemForm.getItemWidth());
    }

    public Item(String itemName,
                ItemCategory itemCategory,
                ItemCondition itemCondition,
                Integer itemValue,
                Integer borrowPrice,
                String description,
                Double itemHeight,
                Double itemWidth) {
    }
}
