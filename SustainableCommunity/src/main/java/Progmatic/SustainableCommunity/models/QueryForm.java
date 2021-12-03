package Progmatic.SustainableCommunity.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class QueryForm {
    private String itemName;
    private String itemCategory;
    private String itemCondition;

    private Integer itemValueGreaterThan;
    private Integer itemValueLesserThan;

    private Integer borrowPriceGreaterThan;
    private Integer borrowPriceLesserThan;

    private boolean itemValueAsc;
    private boolean borrowPriceAsc;

    private boolean isAvailable;
}

