package Progmatic.SustainableCommunity.testConfigs;

import Progmatic.SustainableCommunity.models.AppUser;
import Progmatic.SustainableCommunity.models.UserRole;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.ArrayList;
import java.util.List;

@TestConfiguration
public class TestConfig {

    @Bean
    @Primary
    public UserDetailsService userDetailsService() { // ezek teszt userek
        List<UserDetails> list = new ArrayList<>();
        list.add(new AppUser("username", "password", UserRole.CUSTOMER));
        list.add(new AppUser("usertwo", "passwordtwo", UserRole.ADMIN));

        return new InMemoryUserDetailsManager(list);


    }
}
