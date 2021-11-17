package Progmatic.SustainableCommunity.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/*@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor*/

@Entity
public class AppUser  { //userdetails

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(length = 100, unique = true)
    private String username; //email

    private String password;

    @CreationTimestamp
    private LocalDateTime regTime;
    private String firstName;
    private String lastName;

    @Enumerated
    private UserRole userRole;




}
