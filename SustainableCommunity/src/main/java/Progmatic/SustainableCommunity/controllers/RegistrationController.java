package Progmatic.SustainableCommunity.controllers;

import Progmatic.SustainableCommunity.services.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class RegistrationController {

    RegistrationService registrationService;

    @GetMapping(path = "confirm")
    public boolean confirm(@RequestParam("token") String token) {

        return registrationService.confirmToken(token);

    }
}
