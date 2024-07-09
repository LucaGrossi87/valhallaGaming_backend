package it.epicode.valhallagaming.controller;

import it.epicode.valhallagaming.dto.stationDTO.StationCreateRequest;
import it.epicode.valhallagaming.dto.stationDTO.StationDeleteResponse;
import it.epicode.valhallagaming.dto.stationDTO.StationEditRequest;
import it.epicode.valhallagaming.dto.stationDTO.StationResponse;
import it.epicode.valhallagaming.entity.Station;
import it.epicode.valhallagaming.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class StationController {
    @Autowired
    private StationService stationService;

    @GetMapping("/boards")
    public List<StationResponse> getAllBoards(){
        return stationService.findAllBoards().stream()
                .map(this::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping("/lans")
    public List<StationResponse> getAllLans(){
        return stationService.findAllLans().stream()
                .map(this::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping("/boards/available")
    public List<StationResponse> getAvailableBoards(@RequestParam("date") String dateParam){
        LocalDate date = LocalDate.parse(dateParam);
        return stationService.findAvailableBoards(date).stream()
                .map(this::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping("/lans/available")
    public List<StationResponse> getAvailableLans(@RequestParam("date") String dateParam){
        LocalDate date = LocalDate.parse(dateParam);
        return stationService.findAvailableLans(date).stream()
                .map(this::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping("/boards/open")
    public List<StationResponse> getOpenBoards(@RequestParam("date") String dateParam){
        LocalDate date = LocalDate.parse(dateParam);
        return stationService.findOpen(date).stream()
                .map(this::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public StationResponse getStationById(@PathVariable Long id){
        Optional<Station> station = stationService.findById(id);
        return station.map(this::convertToDTO).orElse(null);
    }

    @PostMapping("/save")
    public StationResponse createBoard(@RequestBody StationCreateRequest stationCreateRequest){
        Station station = convertToEntity(stationCreateRequest);
        Station savedStation = stationService.save(station);
        return convertToDTO(savedStation);
    }

    @PutMapping("/boards/{id}")
    public StationResponse editBoard (@PathVariable Long id, @RequestBody StationEditRequest stationEditRequest){
        Station station = convertToEntity(stationEditRequest);
        station.setId(id);
        Station updatedBoard = stationService.save(station);
        return convertToDTO(updatedBoard);
    }

    @DeleteMapping("/{id}")
    public StationDeleteResponse deleteStation (@PathVariable Long id){
        stationService.deleteById(id);
        StationDeleteResponse response = new StationDeleteResponse();
        response.setId(id);
        response.setMessage("Postazione eliminata");
        return response;
    }

    private StationResponse convertToDTO (Station station){
        StationResponse dto = new StationResponse();
        dto.setId(station.getId());
        dto.setBookingList(station.getBookingList());
        dto.setStationType(station.getStationType());
        dto.setSeatsTotal(station.getSeatsTotal());
        return dto;
    }

    private Station convertToEntity (StationCreateRequest dto){
        return new Station(dto.getBookingList(),dto.getStationType(),dto.getSeatsTotal());
    }

    private Station convertToEntity (StationEditRequest dto){
        return new Station(dto.getBookingList(),dto.getStationType(),dto.getSeatsTotal());
    }

    @GetMapping("/boards/byseats")
    public List<StationResponse> getBoardBySeatsTotal(@RequestParam("date") String dateParam, @RequestParam("seats") int seats){
        LocalDate date = LocalDate.parse(dateParam);
        return stationService.findBySeatsTotal(date, seats).stream()
                .map(this::convertToDTO).collect(Collectors.toList());
    }
}
