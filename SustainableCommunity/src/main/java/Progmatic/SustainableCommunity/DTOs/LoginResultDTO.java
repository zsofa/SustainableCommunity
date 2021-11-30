package Progmatic.SustainableCommunity.DTOs;

import Progmatic.SustainableCommunity.models.AppUser;
import lombok.Getter;
import lombok.Setter;



public class LoginResultDTO {
    @Getter
    @Setter
    AppUser user;

    @Getter
    @Setter
    String token;

    public LoginResultDTO() {
    }
}
