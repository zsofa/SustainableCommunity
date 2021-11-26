package Progmatic.SustainableCommunity.registration;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
@ToString
public class RegistrationRequest {

    private final String username;
    private final String email;
    private final String password;



}
