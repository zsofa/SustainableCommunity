package Progmatic.SustainableCommunity.models;

import java.time.LocalDateTime;

public class AppUser  { //userdetails

    private Long userId;
    private String username; //email
    private String password;

    private LocalDateTime regtime;
    private String firstName;
    private String lastName;

    private UserRole userRole;

}
