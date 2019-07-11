package eu.treative.spacetourism.repository;

import eu.treative.spacetourism.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepository extends JpaRepository<Flight, Long> {
}
