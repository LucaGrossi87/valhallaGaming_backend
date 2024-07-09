package it.epicode.valhallagaming.dto.adminDTO;

import lombok.Data;

@Data
public class AuthenticationRequest {
    private String username;
    private String password;
}