package it.epicode.valhallagaming.dto.userDTO;

import it.epicode.valhallagaming.entity.Booking;
import lombok.Data;

import java.util.List;

@Data
public class UserCreateRequest {
    private String firstName;
    private String lastName;
    private String email;
    private List<Booking> bookingList;
    private final String method = "create";
}
