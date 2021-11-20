package Progmatic.SustainableCommunity.jpaRepos;

import Progmatic.SustainableCommunity.models.AppUser;
import Progmatic.SustainableCommunity.models.UserRole;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

class UserRepoTest {

    @Autowired
    UserRepo userRepo;




  @Test
    void findUserByEmailTest() {
        userRepo.save(new AppUser("Jancsi", "jancsi@gmail.com", UserRole.CUSTOMER));
        //assertNull(userRepo.findAppUserByEmail("jancsi@gmail.com"));
    }



}