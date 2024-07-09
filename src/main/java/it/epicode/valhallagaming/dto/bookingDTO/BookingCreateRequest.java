package it.epicode.valhallagaming.dto.bookingDTO;

import it.epicode.valhallagaming.entity.Station;
import it.epicode.valhallagaming.entity.User;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingCreateRequest {
    private User user;
    private Station station;
    private LocalDate date;
    private boolean open;
    private boolean confirmed;
    private int guests;
    private int seatsAvailable;
    private String game;
    private String note;
    private final String method = "create";
}
