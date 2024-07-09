package it.epicode.valhallagaming.service;

import it.epicode.valhallagaming.entity.Booking;
import it.epicode.valhallagaming.entity.Station;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Iterator;

@Service
public class StationSchedulingService {

    @Autowired
    private StationService stationService;

    @Scheduled(cron = "0 0 0 * * ?")  // Esegui ogni giorno a mezzanotte
    @Transactional
    public void resetAvailability(){
        List<Station> stationsList = stationService.findAll();
        LocalDate currentDate = LocalDate.now();

        for (Station station : stationsList){
            List<Booking> bookings = station.getBookingList();

            Iterator<Booking> iterator = bookings.iterator();
            while (iterator.hasNext()) {
                Booking booking = iterator.next();
                if (booking.getDate().isBefore(currentDate)) {
                    iterator.remove();
                }
            }
            stationService.save(station);
        }
    }
}
