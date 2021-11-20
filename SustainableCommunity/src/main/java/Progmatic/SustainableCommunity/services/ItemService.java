package Progmatic.SustainableCommunity.services;

import Progmatic.SustainableCommunity.forms.ItemForm;
import Progmatic.SustainableCommunity.jpaRepos.ItemRepo;
import Progmatic.SustainableCommunity.models.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    private ItemRepo itemRepo;

    @Autowired
    public ItemService(ItemRepo itemRepo) {
        this.itemRepo = itemRepo;
    }

    private void uploadItem(ItemForm item) {
        Item saveItem = new Item(item);
        itemRepo.save(saveItem);
    }

    /**
     * Egy adott tárgy kölcsönzésekor mennyit lehet vele spórolni.
     * 1 napra vonatkozóan.
     */
    public Integer moneySaved(Item item) {
        return item.getItemValue() - item.getBorrowPrice();
    }

    /**
     * Egy adott tárgy kölcsönzésekor mekkora helyet lehet vele spórolni.
     * cm-ben megadva, metódus átválja méterre, hogy nm-ert kapjunk.
     */

    private Double spaceSaved(Item item) {
        return item.getItemHeight() * item.getItemWidth() / 100;
    }

}

