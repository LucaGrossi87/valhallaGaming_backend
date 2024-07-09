package it.epicode.valhallagaming.dto.bookingDTO;

import lombok.Data;

@Data
public class BookingDeleteResponse {
    private Long id;
    private String message;
    private final String method = "delete";
}
