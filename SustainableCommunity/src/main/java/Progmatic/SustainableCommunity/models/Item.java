package Progmatic.SustainableCommunity.models;

import Progmatic.SustainableCommunity.DTOs.ItemDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    //@Column(nullable = false)
    private String itemName;
    @Enumerated(EnumType.STRING)
    private ItemCategory itemCategory;
    @Enumerated(EnumType.STRING)
    private ItemCondition itemCondition;
    private Integer itemValue;
    private Integer borrowPrice;
    @Column(length = 1200)
    private String description;
    //@Column(nullable = false)
    /**
     * Height, Width: cm-ben adja meg a User.
     */
    private Double itemHeight;
    //@Column(nullable = false)
    private Double itemWidth;
    @Lob
    private byte[] itemImage;
    private String imgLink;
    private Boolean isAvailable;
    private Boolean isApproved;
    @Enumerated(EnumType.STRING)
    private ItemStatus itemStatus;
    @CreationTimestamp
    private LocalDateTime upload;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private AppUser owner;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private AppUser charterer;
    private Integer rateCounter = 0;
    private Double ratings;
    private Double itemRating;
    private Double wasteSavedFromLandfill;

    @Transient
    private Integer moneySaved;
    @Transient
    private Double spaceSaved;

    public Item() {
    }
/*
    public Item(String itemName, ItemCategory itemCategory, ItemCondition itemCondition, Integer itemValue,
                Integer borrowPrice, String description, Double itemHeight,
                Double itemWidth, Boolean isAvailable, Boolean isApproved, LocalDateTime upload, AppUser owner) {
        this();
        this.itemName = itemName;
        this.itemCategory = itemCategory;
        this.itemCondition = itemCondition;
        this.itemValue = itemValue;
        this.borrowPrice = borrowPrice;
        this.description = description;
        this.itemHeight = itemHeight;
        this.itemWidth = itemWidth;
        this.isAvailable = isAvailable;
        this.isApproved = isApproved;
        this.upload = upload;
        this.owner = owner;
    }
    */

    public Item(String itemName, ItemCategory itemCategory, ItemCondition itemCondition, Integer itemValue,
                Integer borrowPrice, String description, Double itemHeight, Double itemWidth,
                byte[] itemImage, Boolean isAvailable, Boolean isApproved, ItemStatus itemStatus,
                AppUser owner, AppUser charterer, Integer rateCounter, Double ratings, Double itemRating,
                Double wasteSavedFromLandfill) {
        this.itemName = itemName;
        this.itemCategory = itemCategory;
        this.itemCondition = itemCondition;
        this.itemValue = itemValue;
        this.borrowPrice = borrowPrice;
        this.description = description;
        this.itemHeight = itemHeight;
        this.itemWidth = itemWidth;
        this.itemImage = itemImage;
        this.isAvailable = isAvailable;
        this.isApproved = isApproved;
        this.itemStatus = itemStatus;
        this.owner = owner;
        this.charterer = charterer;
        this.rateCounter = rateCounter;
        this.ratings = ratings;
        this.itemRating = itemRating;
        this.wasteSavedFromLandfill = wasteSavedFromLandfill;
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

    public Item(ItemDTO itemDTO){
        this(itemDTO.getItemName(),
                itemDTO.getItemCategory(),
                itemDTO.getItemCondition(),
                itemDTO.getItemValue(),
                itemDTO.getBorrowPrice(),
                itemDTO.getDescription(),
                itemDTO.getItemHeight(),
                itemDTO.getItemWidth(),
                itemDTO.getItemImage());
    }

    public Item(String itemName,
                ItemCategory itemCategory,
                ItemCondition itemCondition,
                Integer itemValue,
                Integer borrowPrice,
                String description,
                Double itemHeight,
                Double itemWidth,
                byte[] itemImage) {
        this.itemName = itemName;
        this.itemCategory = itemCategory;
        this.itemCondition = itemCondition;
        this.itemValue = itemValue;
        this.borrowPrice = borrowPrice;
        this.description = description;
        this.itemHeight = itemHeight;
        this.itemWidth = itemWidth;
        this.itemImage = itemImage;

    }

    public Item(String itemName,
                ItemCategory itemCategory,
                ItemCondition itemCondition,
                Integer itemValue,
                Integer borrowPrice,
                String description,
                Double itemHeight,
                Double itemWidth,
                byte[] itemImage,
                String imgLink) {
        this.itemName = itemName;
        this.itemCategory = itemCategory;
        this.itemCondition = itemCondition;
        this.itemValue = itemValue;
        this.borrowPrice = borrowPrice;
        this.description = description;
        this.itemHeight = itemHeight;
        this.itemWidth = itemWidth;
        this.itemImage = itemImage;
        this.imgLink = imgLink;

    }

    public Integer getMoneySaved() {
        return itemValue - borrowPrice;
    }

    public Double getSpaceSaved() {
        return itemHeight * itemWidth /100;
    }
}
