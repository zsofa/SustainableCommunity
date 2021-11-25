package Progmatic.SustainableCommunity.services;

import Progmatic.SustainableCommunity.email.EmailSender;
import Progmatic.SustainableCommunity.exceptions.EmailNotFoundException;
import Progmatic.SustainableCommunity.jpaRepos.UserRepo;
import Progmatic.SustainableCommunity.models.AppUser;
import Progmatic.SustainableCommunity.registration.RegistrationRequest;
import Progmatic.SustainableCommunity.registration.token.ConfirmationToken;
import Progmatic.SustainableCommunity.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService implements UserDetailsService {

    private String emailRegex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|" +
            "\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])" +
            "*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|" +
            "[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*" +
            "[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";



    @PersistenceContext
    private EntityManager em;

    private UserRepo userRepo;

    private final ConfirmationTokenService confService;

    private PasswordEncoder encoder;

    private EmailSender emailSender;

    public UserService( EntityManager em, UserRepo userRepo, ConfirmationTokenService confService, PasswordEncoder encoder, EmailSender emailSender) {

        this.em = em;
        this.userRepo = userRepo;
        this.confService = confService;
        this.encoder = encoder;
        this.emailSender = emailSender;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return em.createQuery("SELECT user FROM AppUser user WHERE user.username = :name", AppUser.class) // kell a model
                .setParameter("name", username)
                .getSingleResult();

    }

    @Transactional
    public AppUser loadUserByEmail(String email) throws EmailNotFoundException {

        return em.createQuery("SELECT user FROM AppUser user WHERE user.email = :email", AppUser.class) // kell a model
                .setParameter("email", email)
                .getSingleResult();

    }


    public List<AppUser> getAll() {
        return userRepo.findAll();
    }

    public AppUser save(AppUser newRegUser) {
        return userRepo.save(newRegUser);
    }



    @Transactional
    public boolean isUsernameUsed(String username) {
        try {
            loadUserByUsername(username);
            return true;
        } catch (Exception e) {

        }
        return false;

    }

    @Transactional
    public boolean isEmailUsed(String email) {
        try {
            loadUserByEmail(email);
            return true;
        } catch (Exception e) {

        }
        return false;

    }

    @Transactional
    public boolean registerR(AppUser user) {
        // todo: handle exceptions
        boolean isValidEmail = emailValidator(user.getEmail());
        try {
            if(!isUsernameUsed(user.getUsername()) && !isEmailUsed(user.getEmail()) && isValidEmail){
                user.setPassword(encoder.encode(user.getPassword()));
                userRepo.save(user);
                return true;

            }


        } catch (Exception e) {

        }

        return false;
    }


    public AppUser getLoggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            Object principal = auth.getPrincipal();
            if (principal instanceof AppUser) {
                return (AppUser) principal;
            }
        }

        return null;
    }


    public boolean emailValidator(String email) {
        String pattern = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|" +
                "\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])" +
                "*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|" +
                "[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*" +
                "[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        return Pattern.compile(pattern)
                .matcher(email)
                .matches();

    }



    @Transactional
    public boolean register(RegistrationRequest user) {
        // todo: handle exceptions
        // todo: bugfix -> if confirm expires the user can't try again because the email is already registered
        // todo: so if(userExist && enabled is false, send new email
        boolean isValidEmail = emailValidator(user.getEmail());
        try {
            if(!isUsernameUsed(user.getUsername()) && !isEmailUsed(user.getEmail()) && isValidEmail){
                 AppUser appUser = new AppUser(
                        user.getUsername(),
                        user.getEmail(),
                        user.getPassword()
                );
                appUser.setPassword(encoder.encode(user.getPassword()));
                userRepo.save(appUser);

                /** *
                 * miután regisztrált a felhasználó, létrejön egy confirm token
                 * kiküldéstől számítva 15 perc áll rendelkezésre a megerősítésre
                 * elmentjük az adatbázisba, összekapcsolva az appuserrel
                 */

                String token = UUID.randomUUID().toString();
                ConfirmationToken confToken = new ConfirmationToken(
                        token,
                        LocalDateTime.now(),
                        LocalDateTime.now().plusMinutes(15),
                        appUser
                );

                confService.saveConfirmationToken(confToken);

               String link ="http://localhost:8080/confirm?token=" + token;
                emailSender.send(user.getEmail(), buildEmail(user.getUsername(), link));
                return true;

            }


        } catch (Exception e) {

        }

        return false;
    }

    @Transactional
    public String confirmToken(String token) {
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
        enableAppUser(
                confirmationToken.getAppUser().getEmail());
        return "confirmed";
    }

    public int enableAppUser(String email) {
        return userRepo.enableAppUser(email);
    }

    // todo: add class for helper and put this method there
    private String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }
}
