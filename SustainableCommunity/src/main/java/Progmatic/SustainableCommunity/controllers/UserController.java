package Progmatic.SustainableCommunity.controllers;

import Progmatic.SustainableCommunity.models.AppUser;
import Progmatic.SustainableCommunity.registration.RegistrationRequest;
import Progmatic.SustainableCommunity.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class UserController {


    UserService userService;


    /*@PostMapping(path = "user/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppUser> create(@RequestBody final AppUser newRegUser) {

        boolean success= userService.register(newRegUser);   //userService.save(newRegUser);
        if(success){
        return new ResponseEntity<>(newRegUser, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(newRegUser,HttpStatus.BAD_REQUEST);

    }*/

    @PostMapping(path = "user/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegistrationRequest> create(@RequestBody final RegistrationRequest newRegUser) {

        boolean success= userService.register(newRegUser);
        if(success){
            return new ResponseEntity<>(newRegUser, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(newRegUser,HttpStatus.BAD_REQUEST);

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

    @GetMapping("/users")
    public List<AppUser> getAllUsers() {
        return userService.getAll();
    }


}
