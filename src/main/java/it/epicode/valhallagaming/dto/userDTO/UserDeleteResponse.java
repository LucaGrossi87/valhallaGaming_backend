package it.epicode.valhallagaming.dto.userDTO;

import lombok.Data;

@Data
public class UserDeleteResponse {
    private Long id;
    private String message;
    private final String method = "delete";
}
