package it.epicode.valhallagaming.dto.adminDTO;

import lombok.Data;

@Data
public class AdminDeleteResponse {
    private Long id;
    private String message;
    private final String method = "delete";
}
