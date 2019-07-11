package eu.treative.spacetourism.repository;

import eu.treative.spacetourism.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepository extends JpaRepository<Flight, Long> {
}
