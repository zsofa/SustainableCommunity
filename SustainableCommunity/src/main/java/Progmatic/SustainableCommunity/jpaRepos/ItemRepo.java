package Progmatic.SustainableCommunity.jpaRepos;

import Progmatic.SustainableCommunity.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepo extends JpaRepository<Item, Long> {
    Optional<Item> findById(Long id);
    Optional<List<Item>> findAllByIsApprovedFalse();
}

