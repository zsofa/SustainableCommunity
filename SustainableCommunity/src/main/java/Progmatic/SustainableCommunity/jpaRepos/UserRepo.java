package Progmatic.SustainableCommunity.jpaRepos;

import Progmatic.SustainableCommunity.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<AppUser, Long> {

    AppUser findAppUserByEmail(String email);
}
