package it.epicode.valhallagaming.dto.adminDTO;

import it.epicode.valhallagaming.entity.Admin;
import lombok.Data;

@Data
public class AuthenticationResponse {
    private final String jwt;
    private final Admin admin;
}