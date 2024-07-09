package it.epicode.valhallagaming.repository;

import it.epicode.valhallagaming.entity.Station;
import it.epicode.valhallagaming.entity.StationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StationRepository extends JpaRepository<Station, Long> {
    List<Station> findByStationType (StationType stationType);
//    List<Station> findBySeatsTotal (int seatsTotal);
}
