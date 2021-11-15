package Progmatic.SustainableCommunity.controllers;

import Progmatic.SustainableCommunity.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

/*
    @Autowired
    UserService userService;

    @PostMapping(path = "user/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegUser> create(@RequestBody final RegUser newRegUser) {
        userService.save(newRegUser);
        return new ResponseEntity<>(newRegUser, HttpStatus.CREATED);
    }


    @GetMapping("user/isUsernameUnique")
    public boolean isUsernameUnique(String name) {
        for(int i= 0; i< userService.getAll().size(); i++){
            if(name.equals(userService.getAll().get(i).getUsername())){
                return false;
            }
        }
        return true;
    }

    */

}
