package Progmatic.SustainableCommunity.DTOs;

import Progmatic.SustainableCommunity.models.Item;
import Progmatic.SustainableCommunity.models.ItemCategory;
import Progmatic.SustainableCommunity.models.ItemCondition;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class UploadedItemDTO {

    private String itemName;
    private ItemCategory itemCategory;
    private ItemCondition itemCondition;
    private Integer borrowPrice;
    private String description;
    private Double itemHeight;
    private Double itemWidth;
    private byte[] itemImage;

    private Integer moneySaved;
    private Double spaceSaved;
    private Double wasteSavedFromLandfill;
    private Double itemRatings;

    public UploadedItemDTO() {
    }

    // ez amikor az oldalon megjelenítjük az itemeket
    public UploadedItemDTO(String itemName,
                   ItemCategory itemCategory,
                   ItemCondition itemCondition,
                   Integer borrowPrice,
                   String description,
                   Double itemHeight,
                   Double itemWidth,
                   byte[] itemImage,
                   Integer moneySaved,
                   Double spaceSaved,
                   Double wasteSavedFromLandfill,
                   Double itemRatings) {
        this.itemName = itemName;
        this.itemCategory = itemCategory;
        this.itemCondition = itemCondition;
        this.borrowPrice = borrowPrice;
        this.description = description;
        this.itemHeight = itemHeight;
        this.itemWidth = itemWidth;
        this.itemImage = itemImage;
        this.moneySaved = moneySaved;
        this.spaceSaved = spaceSaved;
        this.wasteSavedFromLandfill = wasteSavedFromLandfill;
        this.itemRatings = itemRatings;
    }

    public UploadedItemDTO(Item item){
        this(item.getItemName(),
                item.getItemCategory(),
                item.getItemCondition(),
                item.getBorrowPrice(),
                item.getDescription(),
                item.getItemHeight(),
                item.getItemWidth(),
                item.getItemImage(),
                item.getMoneySaved(),
                item.getSpaceSaved(),
                item.getWasteSavedFromLandfill(),
                item.getItemRating());
    }
}
