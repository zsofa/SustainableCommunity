package Progmatic.SustainableCommunity.controllers;

import Progmatic.SustainableCommunity.models.AppUser;
import Progmatic.SustainableCommunity.models.UserRole;
import Progmatic.SustainableCommunity.registration.RegistrationRequest;
import Progmatic.SustainableCommunity.services.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class UserController {


    UserService userService;

    //LoginResultDTO loginResultDTO;


   @PostMapping(path = "user/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppUser> create(@RequestBody final AppUser newRegUser) {

        boolean success= userService.register(newRegUser);   //userService.save(newRegUser);
        if(success){
        return new ResponseEntity<>(newRegUser, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(newRegUser,HttpStatus.BAD_REQUEST);

    }
/*
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
*/

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
    public ResponseEntity<List<AppUser>> getAllUsers() {
        AppUser currentUser=  userService.getLoggedInUser();
        if (currentUser.getUserRole() != UserRole.ADMIN)
            return new ResponseEntity<>((List<AppUser>)null, HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam("user") String username, @RequestParam("password") String pwd) {
//TODO auth. before
        AppUser user = userService.loginUser(username, pwd);

        if (user == null)
            return new ResponseEntity<>((String)null, HttpStatus.UNAUTHORIZED);

        String token = getJWTToken(username, user.getUserId());// 1 helylre userid TODO
        return new ResponseEntity<>("\""+token+"\"", HttpStatus.OK);

    }

/*    @PostMapping("/login")
    public ResponseEntity<LoginResultDTO> login(@RequestParam("user") String username, @RequestParam("password") String pwd) {
//TODO auth. before
        AppUser user = userService.loginUser(username, pwd);

        if (user == null)
            return new ResponseEntity<>((LoginResultDTO)null, HttpStatus.UNAUTHORIZED);

        String token = getJWTToken(username, 1);// 1 helylre userid TODO

        LoginResultDTO result = new LoginResultDTO();
        result.token = token;
        result.user = user;

        return new ResponseEntity<>(result, HttpStatus.OK);

    }*/

    private String getJWTToken(String username, long userID) {
        String secretKey = "wGvcqQzJyidy";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
                .builder()
                .setId("" + userID)
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        return token;
    }

    @DeleteMapping("user/delete/{id}")
    public ResponseEntity<AppUser> deleteSelfUserAccount(@PathVariable Long id) {
        return new ResponseEntity<>(new AppUser(), HttpStatus.OK);
    }


    @PutMapping("user/edit/{id}")
    public ResponseEntity<AppUser> editSelfUserAccount(@PathVariable Long id) {
        return new ResponseEntity<>(new AppUser(), HttpStatus.OK);
    }

    @DeleteMapping("admin/delete/{id}")
    public ResponseEntity<AppUser> deleteAnyAccount(@PathVariable Long id) {
        return new ResponseEntity<>(new AppUser(), HttpStatus.OK);
    }

    @PutMapping("admin/edit/{id}")
    public ResponseEntity<AppUser> editAnyAccount(@PathVariable Long id) {
        return new ResponseEntity<>(new AppUser(), HttpStatus.OK);
    }

}
