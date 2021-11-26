package Progmatic.SustainableCommunity.services;

import Progmatic.SustainableCommunity.exceptions.EmailNotFoundException;
import Progmatic.SustainableCommunity.jpaRepos.UserRepo;
import Progmatic.SustainableCommunity.models.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.List;
import java.util.Optional;
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

    @Autowired
    private UserRepo userRepo;

    private PasswordEncoder encoder;

    @Autowired
    public UserService(PasswordEncoder encoder) {
        this.encoder = encoder;
    }


    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return em.createQuery("SELECT user FROM AppUser user WHERE user.username = :name", AppUser.class) // kell a model
                .setParameter("name", username)
                .getSingleResult();

    }

    public AppUser getUser(long id) {
        return userRepo.findById(id).orElse(null);

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
