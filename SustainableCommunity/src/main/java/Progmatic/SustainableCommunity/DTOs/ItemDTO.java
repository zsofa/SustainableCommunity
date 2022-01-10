package Progmatic.SustainableCommunity.DTOs;

import Progmatic.SustainableCommunity.models.Item;
import Progmatic.SustainableCommunity.models.ItemCategory;
import Progmatic.SustainableCommunity.models.ItemCondition;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemDTO {

    private String itemName;
    private ItemCategory itemCategory;
    private ItemCondition itemCondition;
    private Integer itemValue;
    private Integer borrowPrice;
    private String description;
    private Double itemHeight;
    private Double itemWidth;
    private byte[] itemImage;

    public ItemDTO() {
    }

    public ItemDTO(String itemName,
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

    public ItemDTO(Item item){
        this(item.getItemName(),
                item.getItemCategory(),
                item.getItemCondition(),
                item.getItemValue(),
                item.getBorrowPrice(),
                item.getDescription(),
                item.getItemHeight(),
                item.getItemWidth(),
                item.getItemImage());
    }
}
