package Progmatic.SustainableCommunity.controllers;

import Progmatic.SustainableCommunity.DTOs.ItemDTO;
import Progmatic.SustainableCommunity.models.AppUser;
import Progmatic.SustainableCommunity.models.Item;
import Progmatic.SustainableCommunity.models.ItemCategory;
import Progmatic.SustainableCommunity.models.UserRole;
import Progmatic.SustainableCommunity.services.ItemService;
import Progmatic.SustainableCommunity.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
public class ItemController {

    ItemService itemService;
    UserService userService;

    @PostMapping(path = "item/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ItemDTO> create(@RequestBody final ItemDTO newItem) {
        AppUser owner = userService.getLoggedInUser();
            itemService.uploadItem(newItem, owner);
            return new ResponseEntity<>(newItem, HttpStatus.CREATED);


    }

    @PostMapping(path = "item/create/img",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ItemDTO> createWImg(@RequestBody final ItemDTO newItem,
                                              @RequestParam("image") MultipartFile file) {
        AppUser owner =// new AppUser("user12", "user12@gmail.com", "password", UserRole.CUSTOMER);
                userService.getLoggedInUser();
        //ItemDTO newItem = new ItemDTO("testItem");
        itemService.uploadItemWImage(newItem, owner,file);
        return new ResponseEntity<>(newItem, HttpStatus.CREATED);
    }

    @GetMapping(path = "itemImg/download/aws/{itemId}",
            produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE}
    )
    public byte[] downloadItemImageFromAWS(@PathVariable("itemId") Long itemId) {
        return itemService.downloadItemImgAWS(itemId);
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
