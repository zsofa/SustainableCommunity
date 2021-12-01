package Progmatic.SustainableCommunity.controllers;

import Progmatic.SustainableCommunity.forms.ItemForm;
import Progmatic.SustainableCommunity.models.AppUser;
import Progmatic.SustainableCommunity.models.Item;
import Progmatic.SustainableCommunity.models.ItemCategory;
import Progmatic.SustainableCommunity.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ItemController {
    @Autowired
    ItemService itemService;

    @PostMapping(path = "item/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ItemForm> create(@RequestBody final ItemForm newItem, AppUser appUser) {
            itemService.uploadItem(newItem, appUser);
            return new ResponseEntity<>(newItem, HttpStatus.CREATED);

    }

    @GetMapping("item/{ItemCategory}/{id}")
    public ResponseEntity<Item> getOneItem(@PathVariable ItemCategory itemCategory, Long id) {
        return new ResponseEntity<>(new Item(), HttpStatus.OK);
    }

// TODO post -> reserve item in ItemService

    @PostMapping("item/reserve")
    public ResponseEntity<Item> reserveItem(@RequestBody Item item, AppUser appUser) {
        itemService.reserveItem(item);
        itemService.addToRentedItemList(item, appUser);
        return new ResponseEntity<>(item, HttpStatus.ACCEPTED);
    }

    @PostMapping("item/return")
    public ResponseEntity<Item> returnItem(@RequestBody Item item) {
        itemService.enableItem(item);
        return new ResponseEntity<>(item, HttpStatus.ACCEPTED);
    }


}
