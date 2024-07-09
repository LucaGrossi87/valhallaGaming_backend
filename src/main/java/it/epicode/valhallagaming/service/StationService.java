package it.epicode.valhallagaming.service;

import it.epicode.valhallagaming.entity.Booking;
import it.epicode.valhallagaming.entity.Station;
import it.epicode.valhallagaming.entity.StationType;
import it.epicode.valhallagaming.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StationService {
    @Autowired
    private StationRepository stationRepository;

    public List<Station> findAll(){
        return stationRepository.findAll();
    }

    public Optional<Station> findById(Long id){
        return stationRepository.findById(id);
    }

    public Station save (Station lan){
        return stationRepository.save(lan);
    }

    public void deleteById (Long id){
        stationRepository.deleteById(id);
    }

    public List<Station> findAllBoards(){
        return stationRepository.findByStationType(StationType.BOARD);
    }

    public List<Station> findAllLans(){
        return stationRepository.findByStationType(StationType.LAN);
    }

    public List<Station> findAvailableBoards(LocalDate date) {
        List<Station> allBoards = stationRepository.findByStationType(StationType.BOARD);
        List<Station> availableBoards = new ArrayList<>();

        for (Station board : allBoards) {
            boolean isAvailable = true;

            for (Booking booking : board.getBookingList()) {
                if (booking.getDate().equals(date)) {
                    isAvailable = false;
                    break;
                }
            }

            if (isAvailable) {
                availableBoards.add(board);
            }
        }

        return availableBoards;
    }

    public List<Station> findBookedBoards(LocalDate date){
        List<Station> allBoards = stationRepository.findByStationType(StationType.BOARD);
        List<Station> bookedBoards=new ArrayList<>();
        for (Station board : allBoards){
            boolean isBooked=true;
            for (Booking booking : board.getBookingList()){
                if (!booking.getDate().equals(date)) {
                    isBooked = false;
                    break;
                }
            }
            if (!isBooked) {
                bookedBoards.add(board);
            }
        }
        return bookedBoards;
    }

    public List<Station> findAvailableLans(LocalDate date){
        List<Station> allLans = stationRepository.findByStationType(StationType.LAN);
        List<Station> availableLans=new ArrayList<>();
        for (Station lan : allLans){
            boolean isAvailable=true;
            for (Booking booking : lan.getBookingList()){
                if (booking.getDate().equals(date)) {
                    isAvailable = false;
                    break;
                }
            }
            if (isAvailable) {
                availableLans.add(lan);
            }
        }
        return availableLans;
    }

    public List<Station> findOpen(LocalDate date) {
        List<Station> bookedBoards = this.findBookedBoards(date);
        List<Station> openBoards = new ArrayList<>();
        for (Station board : bookedBoards) {
            for (Booking booking : board.getBookingList()) {
                if (booking.getDate().equals(date)) {
                    if (booking.isOpen()) {
                        openBoards.add(board);
                    }
                }
            }
            }

        return openBoards;
        }

    public List<Station> findBySeatsTotal(LocalDate date, int seatsTotal) {
        List<Station> availableBoards = findAvailableBoards(date);
        List<Station> boardsBySeats = new ArrayList<>();

        for (Station board : availableBoards) {
            if (board.getSeatsTotal() == seatsTotal) {
                boardsBySeats.add(board);
            }
        }

        return boardsBySeats;
    }
        }

