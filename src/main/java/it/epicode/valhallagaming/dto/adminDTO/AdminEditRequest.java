package it.epicode.valhallagaming.dto.adminDTO;

import it.epicode.valhallagaming.entity.Role;
import lombok.Data;

@Data
public class AdminEditRequest {
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private String password;
    private boolean loggedin;
    private Role role;
    private final String method = "edit";
}
