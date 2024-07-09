package it.epicode.valhallagaming.dto.adminDTO;

import it.epicode.valhallagaming.entity.Role;
import lombok.Data;

@Data
public class AdminResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private boolean loggedin;
    private Role role;
}
