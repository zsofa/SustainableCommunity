package Progmatic.SustainableCommunity.jpaRepos;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<String, Long> {
    // TODO a String helyett majd a "jó" User class-t kell használni!

}
