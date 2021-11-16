package Progmatic.SustainableCommunity.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppUser  { //userdetails

    private Long userId;
    private String username; //email
    private String password;

    private LocalDateTime regTime;
    private String firstName;
    private String lastName;

    private UserRole userRole;


}
