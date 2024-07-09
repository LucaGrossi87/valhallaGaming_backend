package it.epicode.valhallagaming.service;

import it.epicode.valhallagaming.entity.Booking;
import it.epicode.valhallagaming.entity.User;
import it.epicode.valhallagaming.repository.BookingRepository;
import it.epicode.valhallagaming.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Booking> findAll(){
        return bookingRepository.findAll();
    }

    public Optional<Booking> findById(Long id){
        return bookingRepository.findById(id);
    }

    public Booking save (Booking booking){
        return bookingRepository.save(booking);
    }

    public void deleteById(Long id) {
        bookingRepository.deleteById(id);
    }

    public List<Booking> getByDate(LocalDate date){
        return bookingRepository.findBookingsByDate(date);
    }

    public List<Booking> findOpen(LocalDate date){
        return bookingRepository.findByOpen(true);
    }

    public List<Booking> findOpenBookingsByDate(LocalDate date) {
        return bookingRepository.findOpenBookingsByDate(date);
    }

    public List<Booking> getBookingsByStationId(Long stationId) {
        return bookingRepository.findByStationId(stationId);
    }

    public void deleteExpiredBookingsAndUsers() {
        LocalDate today = LocalDate.now();
        List<Booking> expiredBookings = bookingRepository.findBookingsByDate(today.minusDays(1));

        for (Booking booking : expiredBookings) {
            User user = booking.getUser();
            bookingRepository.deleteById(booking.getId());

            if (user.getBookingList().isEmpty()) {
                userRepository.deleteById(user.getId());
            }
        }
    }

}
