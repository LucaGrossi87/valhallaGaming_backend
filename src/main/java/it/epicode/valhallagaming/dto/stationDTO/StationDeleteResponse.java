package it.epicode.valhallagaming.dto.stationDTO;

import lombok.Data;

@Data
public class StationDeleteResponse {
    private Long id;
    private String message;
    private final String method = "delete";
}
