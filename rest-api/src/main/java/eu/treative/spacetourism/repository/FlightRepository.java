package eu.treative.spacetourism.repository;

import eu.treative.spacetourism.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {

    List<Flight> findFlightsByTouristsId(Long id);

}
