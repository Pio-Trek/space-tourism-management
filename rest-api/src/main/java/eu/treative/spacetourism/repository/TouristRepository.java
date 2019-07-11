package eu.treative.spacetourism.repository;

import eu.treative.spacetourism.entity.Tourist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TouristRepository extends JpaRepository<Tourist, Long> {
}
