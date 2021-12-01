package Progmatic.SustainableCommunity.controllers;

import Progmatic.SustainableCommunity.DTOs.ItemDTO;
import Progmatic.SustainableCommunity.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemController {
    @Autowired
    ItemService itemService;

    @PostMapping(path = "item/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ItemDTO> create(@RequestBody final ItemDTO newItem) {
            itemService.uploadItem(newItem);
            return new ResponseEntity<>(newItem, HttpStatus.CREATED);


    }


}
