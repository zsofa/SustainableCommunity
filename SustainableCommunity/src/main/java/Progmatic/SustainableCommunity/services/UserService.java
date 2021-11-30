package Progmatic.SustainableCommunity.services;

//import Progmatic.SustainableCommunity.email.EmailBuilder;
import Progmatic.SustainableCommunity.email.EmailBuilder;
import Progmatic.SustainableCommunity.email.EmailSender;
import Progmatic.SustainableCommunity.exceptions.EmailNotFoundException;
import Progmatic.SustainableCommunity.jpaRepos.UserRepo;
import Progmatic.SustainableCommunity.models.AppUser;
import Progmatic.SustainableCommunity.models.Item;
import Progmatic.SustainableCommunity.registration.RegistrationRequest;
import Progmatic.SustainableCommunity.registration.token.ConfirmationToken;
import Progmatic.SustainableCommunity.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {


    @PersistenceContext
    private EntityManager em;

    private UserRepo userRepo;

    private final ConfirmationTokenService confService;

    @Autowired
    private PasswordEncoder encoder;

    private EmailSender emailSender;
    private EmailBuilder builder;


    @Transactional
    @Override
    public AppUser loadUserByUsername(String username) throws UsernameNotFoundException {
        return em.createQuery("SELECT user FROM AppUser user WHERE user.username = :name", AppUser.class) // kell a model
                .setParameter("name", username)
                .getSingleResult();

    }

    public AppUser getUser(long id) {
        return userRepo.findById(id).orElse(null);

    }

    @Transactional
    public AppUser loginUser(String username, String password) {

        AppUser user;
        try {
            user = loadUserByUsername(username);
        } catch(NoResultException ex) {
            return null;
        }



        if (encoder.matches(password,user.getPassword())) {
            return user;
        }

        return null;
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
    public boolean register(AppUser user) {
        try {
            if(!isUsernameUsed(user.getUsername()) && !isEmailUsed(user.getEmail())){
                user.setPassword(encoder.encode(user.getPassword()));
                userRepo.save(user);
                return true;

            }


        } catch (Exception e) {

        }

        return false;
    }


   /*
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

                *//** *
                 * miután regisztrált a felhasználó, létrejön egy confirm token
                 * kiküldéstől számítva 15 perc áll rendelkezésre a megerősítésre
                 * elmentjük az adatbázisba, összekapcsolva az appuserrel
                 *//*

                String token = UUID.randomUUID().toString();
                ConfirmationToken confToken = new ConfirmationToken(
                        token,
                        LocalDateTime.now(),
                        LocalDateTime.now().plusMinutes(15),
                        appUser
                );

                confService.saveConfirmationToken(confToken);

               String link ="http://localhost:8080/confirm?token=" + token;
               emailSender.send(user.getEmail(), builder.buildEmail(user.getUsername(), link));
                return true;

            }


        } catch (Exception e) {

        }

        return false;
    }

*/

    public int enableAppUser(String email) {
        return userRepo.enableAppUser(email);
    }

    public List<Item> getItemRatingList(Long id) {
        return em.createQuery("SELECT item FROM Item item WHERE item.owner.userId =:id", Item.class)
                .setParameter("id",id)
                .getResultList();
    }


       public Double getUserRating(Long userId) {

        List<Item> itemsByUserId = getItemRatingList(userId);
        Double userRating = 0.0;
        double counter = 0.0;

        for (Item item : itemsByUserId) {
            if(item.getItemRating() != 0.0) {
                counter++;
                userRating += item.getItemRating();
            }
        }
        return (userRating/counter);

    }
}
