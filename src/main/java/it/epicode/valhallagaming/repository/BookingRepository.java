package it.epicode.valhallagaming.repository;

import it.epicode.valhallagaming.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findBookingsByDate (LocalDate date);
    List<Booking> findByOpen (Boolean open);

    @Query("SELECT b FROM Booking b WHERE b.date = :date AND b.open = true")
    List<Booking> findOpenBookingsByDate(LocalDate date);

    @Query("SELECT b FROM Booking b WHERE b.station.id = :stationId")
    List<Booking> findByStationId(Long stationId);
}
