package it.epicode.valhallagaming.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.beans.factory.annotation.Autowired;
import it.epicode.valhallagaming.service.BookingService;

@Configuration
@EnableScheduling
public class SchedulingConfig {

    @Autowired
    private BookingService bookingService;

    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteExpiredBookings() {
        bookingService.deleteExpiredBookingsAndUsers();
    }
}
