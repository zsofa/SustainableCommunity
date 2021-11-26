package Progmatic.SustainableCommunity.services;

import Progmatic.SustainableCommunity.registration.token.ConfirmationToken;
import Progmatic.SustainableCommunity.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {

    ConfirmationTokenService confService;
    UserService userService;

    @Transactional
    public boolean confirmToken(String token) {

        ConfirmationToken confirmationToken = confService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        confService.setConfirmedAt(token);
        userService.enableAppUser(
                confirmationToken.getAppUser().getEmail());
        return true;
    }
}
