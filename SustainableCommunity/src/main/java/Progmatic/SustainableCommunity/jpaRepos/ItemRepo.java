package Progmatic.SustainableCommunity.jpaRepos;

import Progmatic.SustainableCommunity.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepo extends JpaRepository<Item, Long> {
}
