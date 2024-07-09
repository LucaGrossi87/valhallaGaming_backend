package it.epicode.valhallagaming.dto.stationDTO;

import it.epicode.valhallagaming.entity.Booking;
import it.epicode.valhallagaming.entity.StationType;
import lombok.Data;

import java.util.List;

@Data
public class StationResponse {
    private Long id;
    private List<Booking> bookingList;
    private int seatsTotal;
    private StationType stationType;
}