package Progmatic.SustainableCommunity.services;

import Progmatic.SustainableCommunity.exceptions.EmailNotFoundException;
import Progmatic.SustainableCommunity.jpaRepos.UserRepo;
import Progmatic.SustainableCommunity.models.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
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

    private PasswordEncoder encoder;

    @Autowired
    public UserService(PasswordEncoder encoder) {
        this.encoder = encoder;
    }


    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//!! Át kellett castolni valamiért , hiba!!
        return (UserDetails) em.createQuery("SELECT user FROM AppUser user WHERE user.username = :name", AppUser.class) // kell a model
                .setParameter("name", username)
                .getSingleResult();

    }

/*

    @Transactional
    public List<User> findAllUsers() {
        return em.createQuery("SELECT user FROM User user", User.class)
                .getResultList();
    }
*/


    // username benne van az adatbázis
    @Transactional
    public boolean isUsernameUsed(String username) {
        try {
            loadUserByUsername(username);
            return true;
        } catch (Exception e) {

        }
        return false;

    }
/*

    public User register(User newUser) {
        for(int i = 0; i < findAllUsers().size();i++){
            if(newUser.getUsername().equals(findAllUsers().get(i).getUsername())){
                return null;
            }
        }
        newUser.setPassword(encoder.encode(newUser.getPassword()));
        return userRepo.save(newUser);
    }

*/


    @Transactional
    public AppUser loadUserByEmail(String email) throws EmailNotFoundException {

        return em.createQuery("SELECT user FROM AppUser user WHERE user.email = :email", AppUser.class) // kell a model
                .setParameter("email", email)
                .getSingleResult();

    }


    @Transactional
    public boolean isEmailUsed(String email) {
        try {
            loadUserByUsername(email);
            return true;
        } catch (Exception e) {

        }
        return false;

    }

    @Transactional
    public boolean register(AppUser user) {
        try {
            if (!isUsernameUsed(user.getUsername())) {
                if(emailValidator(user.getEmail(),emailRegex)){
                if (!isEmailUsed(user.getUsername())) {
                    user.setPassword(encoder.encode(user.getPassword()));
                    em.persist(user);
                    return true;
                }
            }}
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


    public boolean emailValidator(String email, String pattern) {
        return Pattern.compile(pattern)
                .matcher(email)
                .matches();

    }


}
