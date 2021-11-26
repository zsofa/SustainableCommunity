package Progmatic.SustainableCommunity.services;

import Progmatic.SustainableCommunity.forms.ItemForm;
import Progmatic.SustainableCommunity.jpaRepos.ItemRepo;
import Progmatic.SustainableCommunity.models.AppUser;
import Progmatic.SustainableCommunity.models.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Double spaceSaved(Item item) {
        return item.getItemHeight() * item.getItemWidth() / 100;
    }

    /**
     *
     * changeItemPrice (Item)
     * Admin jogosultság, kikeresi az Itemet id alapján, átállítja az isApprovalt.
     */

    private void approveItem(Item toBeApproved, Integer price) {
        Optional<Item> op = itemRepo.findById(toBeApproved.getItemId());
        if (op.isPresent()) {
            Item toApproved = op.get();
            toApproved.setBorrowPrice(price);
            toApproved.setIsApproved(true);
            itemRepo.save(toApproved);
        }
    }

    /**
     * Visszaadja az Admin számára a még nem approvalt itemeket.
     */

    private Optional<List<Item>> approveItemList() {
        return itemRepo.findAllByIsApprovedFalse();
    }

    /**
     * Kiszámolja az Item ratingjét.
     * @param rating
     * @param item
     * @return
     */
    public Double getItemRate(Double rating, Item item){
        Double rate = item.getRatings() + rating;
        item.setRatings(rate);
        item.setRateCounter(item.getRateCounter()+1);
        return item.getRatings()/item.getRateCounter();
    }
}

