package it.epicode.valhallagaming.dto.stationDTO;

import it.epicode.valhallagaming.entity.Booking;
import it.epicode.valhallagaming.entity.StationType;
import lombok.Data;

import java.util.List;

@Data
public class StationEditRequest {
    private List<Booking> bookingList;
    private int seatsTotal;
    private StationType stationType;
    private final String method = "edit";
    }

