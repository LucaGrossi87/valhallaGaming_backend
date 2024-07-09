package it.epicode.valhallagaming.controller;

import it.epicode.valhallagaming.dto.bookingDTO.BookingCreateRequest;
import it.epicode.valhallagaming.dto.bookingDTO.BookingDeleteResponse;
import it.epicode.valhallagaming.dto.bookingDTO.BookingEditRequest;
import it.epicode.valhallagaming.dto.bookingDTO.BookingResponse;
import it.epicode.valhallagaming.entity.Admin;
import it.epicode.valhallagaming.entity.Booking;
import it.epicode.valhallagaming.entity.Station;
import it.epicode.valhallagaming.entity.User;
import it.epicode.valhallagaming.service.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/bookings")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private StationService stationService;

    @Autowired
    private UserService userService;

    @GetMapping
    public List<BookingResponse> getAllBookings() {
        return bookingService.findAll().stream()
                .map(this::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public BookingResponse getBookingById(@PathVariable Long id) {
        Optional<Booking> booking = bookingService.findById(id);
        if (booking.isPresent()) {
            return convertToDTO(booking.get());
        } else {
            throw new EntityNotFoundException("Booking not found with id: " + id);
        }
    }

    @PostMapping
    public BookingResponse createBooking(@RequestBody BookingCreateRequest bookingCreateRequest) {
        Booking booking = convertToEntity(bookingCreateRequest);
        Booking savedBooking = bookingService.save(booking);
        return convertToDTO(savedBooking);
    }

    @PutMapping("/{id}")
    public BookingResponse editBooking(@PathVariable Long id, @RequestBody BookingEditRequest bookingEditRequest) {
        Booking booking = bookingService.findById(id).orElseThrow(() -> new EntityNotFoundException("Booking not found"));
        booking.setSeatsAvailable(bookingEditRequest.getSeatsAvailable());
        Booking updatedBooking = bookingService.save(booking);
        return convertToDTO(updatedBooking);
    }

    @DeleteMapping("/{id}")
    public BookingDeleteResponse deleteBooking(@PathVariable Long id) {
        bookingService.deleteById(id);
        BookingDeleteResponse response = new BookingDeleteResponse();
        response.setId(id);
        response.setMessage("Booking eliminato");
        return response;
    }

    private BookingResponse convertToDTO(Booking booking) {
        BookingResponse dto = new BookingResponse();
        dto.setId(booking.getId());
        dto.setUser(booking.getUser());
        dto.setStation(booking.getStation());
        dto.setDate(booking.getDate());
        dto.setConfirmed(booking.isConfirmed());
        dto.setGuests(booking.getGuests());
        dto.setSeatsAvailable(booking.getSeatsAvailable());
        dto.setOpen(booking.isOpen());
        dto.setGame(booking.getGame());
        dto.setNote(booking.getNote());
        return dto;
    }

    private Booking convertToEntity(BookingCreateRequest dto) {
        return new Booking(dto.getUser(), dto.getStation(), dto.getDate(), dto.isOpen(), dto.isConfirmed(), dto.getGuests(), dto.getSeatsAvailable(), dto.getGame(),dto.getNote());
    }

    private Booking convertToEntity(BookingEditRequest dto) {
        return new Booking(dto.getUser(), dto.getStation(), dto.getDate(), dto.isOpen(), dto.isConfirmed(), dto.getGuests(), dto.getSeatsAvailable(), dto.getGame(), dto.getNote());
    }

    @PutMapping("/{id}/confirmation")
    public void sendEmailConfirmation(@PathVariable Long id) {
        Optional<Booking> bookingOpt = bookingService.findById(id);
        if (bookingOpt.isPresent()) {
            Booking booking = bookingOpt.get();
            User user = booking.getUser();
            booking.setConfirmed(true);
            bookingService.save(booking);
            Optional<Admin> loggedAdminOpt = adminService.findLoggedin();
            if (loggedAdminOpt.isPresent()) {
                Admin loggedAdmin = loggedAdminOpt.get();
                String userEmail = user.getEmail();
                String adminName = loggedAdmin.getFirstName();
                String adminEmail = loggedAdmin.getEmail();
                emailService.sendConfirmationEmail(userEmail, adminName, adminEmail);
            }
        }
    }

    @PutMapping("/{id}/delete")
    public void sendEmailDelete(@PathVariable Long id) {
        Optional<Booking> bookingOpt = bookingService.findById(id);
        if (bookingOpt.isPresent()) {
            Booking booking = bookingOpt.get();
            User user = booking.getUser();

            booking.setConfirmed(true);
            bookingService.save(booking);

            Optional<Admin> loggedAdminOpt = adminService.findLoggedin();
            if (loggedAdminOpt.isPresent()) {
                Admin loggedAdmin = loggedAdminOpt.get();
                String userEmail = user.getEmail();
                String adminName = loggedAdmin.getFirstName();
                String adminEmail = loggedAdmin.getEmail();

                emailService.sendDeleteEmail(userEmail);
            }
        }
    }

    @PostMapping("/boardbookingclose")
    public BookingResponse boardBookingClose(@RequestParam("date") String dateParam, @RequestParam("guests") int guests, @RequestParam("userId") Long userId, @RequestParam("open") boolean open,@RequestParam("game") String game, String note) {
        LocalDate date = LocalDate.parse(dateParam);
        User user = userService.findById(userId).orElseThrow(() -> new EntityNotFoundException("Utente non trovato"));
        Station finalBoard = null;
        List<Station> boardsList = stationService.findAvailableBoards(date);

        if (!boardsList.isEmpty()) {
            int closestDiff = Integer.MAX_VALUE;

            for (Station board : boardsList) {
                int seatsTotal = board.getSeatsTotal();
                if (seatsTotal >= guests) {
                    int diff = seatsTotal - guests;
                    if (diff < closestDiff) {
                        closestDiff = diff;
                        finalBoard = board;
                    }
                }
            }

            if (finalBoard != null) {
                Booking newClosedBooking = new Booking(user, finalBoard, date, open, false, guests, finalBoard.getSeatsTotal() - guests, game, "");
                Booking newBooking = bookingService.save(newClosedBooking);
                return convertToDTO(newBooking);
            } else {
                throw new EntityNotFoundException("Postazione non trovata");
            }
        } else {
            throw new EntityNotFoundException("Postazione non trovata");
        }
    }

    @PostMapping("/lanbooking")
    public BookingResponse lanBooking(@RequestParam("date") String dateParam, @RequestParam("userId") Long userId) {
        LocalDate date = LocalDate.parse(dateParam);
        User user = userService.findById(userId).orElseThrow(() -> new EntityNotFoundException("Utente non trovato"));
        List<Station> lansList = stationService.findAvailableLans(date);
        if (!lansList.isEmpty()) {
            Station lan = lansList.get(0);
            Booking newLanBooking = new Booking(user, lan, date, false, false, 1, 0,"LAN","");
            Booking newBooking = bookingService.save(newLanBooking);
            return convertToDTO(newBooking);
        } else {
            throw new EntityNotFoundException("Postazione non trovata");
        }
    }

    @GetMapping("/bookingsbydate")
    public List<BookingResponse> getBookingByDate(@RequestParam("date") String dateParam) {
        LocalDate date = LocalDate.parse(dateParam);
        return bookingService.getByDate(date).stream()
                .map(this::convertToDTO).collect(Collectors.toList());
    }

    @PostMapping("/bookingbyid")
    public ResponseEntity boardBookingById(@RequestParam("date") String dateParam,
                                            @RequestParam("guests") int guests,
                                            @RequestParam("userId") Long userId,
                                            @RequestParam("open") boolean open,
                                            @RequestParam("boardId") Long boardId,
                                            @RequestParam("game") String game) {
        try {
            LocalDate date = LocalDate.parse(dateParam);

            User user = userService.findById(userId)
                    .orElseThrow(() -> new EntityNotFoundException("Utente non trovato"));
            Optional<Station> optionalStation = stationService.findById(boardId);
            if (optionalStation.isEmpty()) {
                throw new EntityNotFoundException("Stazione non trovata con id: " + boardId);
            }
            Station chosenBoard = optionalStation.get();

            Booking newClosedBooking = new Booking(user, chosenBoard, date, open, false, guests, chosenBoard.getSeatsTotal() - guests, game, "");
            Booking newBooking = bookingService.save(newClosedBooking);

            return ResponseEntity.ok(convertToDTO(newBooking));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore durante la prenotazione: " + e.getMessage());
    }}

    @GetMapping("/openbookings")
    public List<BookingResponse> getOpenBookingsByDate(@RequestParam("date") String dateParam) {
        LocalDate date = LocalDate.parse(dateParam);
        return bookingService.findOpenBookingsByDate(date).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}/note")
    public BookingResponse updateBookingNote(@PathVariable Long id, @RequestBody String note) {
        Booking booking = bookingService.findById(id).orElseThrow(() -> new EntityNotFoundException("Booking not found"));
        booking.setNote(note);
        Booking updatedBooking = bookingService.save(booking);
        return convertToDTO(updatedBooking);
    }

    @GetMapping("/station/{stationId}")
    public ResponseEntity<List<Booking>> getBookingsByStationId(@PathVariable Long stationId) {
        List<Booking> bookings = bookingService.getBookingsByStationId(stationId);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }
}