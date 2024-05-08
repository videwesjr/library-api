package com.project.libraryApi.controller;

import com.project.libraryApi.dto.BookAvailableDTO;
import com.project.libraryApi.dto.BookListDTO;
import com.project.libraryApi.dto.ReservationDTO;
import com.project.libraryApi.model.Book;
import com.project.libraryApi.model.Reservation;
import com.project.libraryApi.service.ReservationService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    Logger log = LoggerFactory.getLogger(ReservationController.class);
    @Autowired
    private ReservationService reservationService;

    @GetMapping
    public ResponseEntity<List<Reservation>> listAllReservations() {
        log.info("Receive request to GET ALL reservations");
        return new ResponseEntity<>(reservationService.getAllReservations(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        log.info("Receive request to GET reservation. id: ".concat(id.toString()));
        Reservation reservation = reservationService.getReservationById(id);
        log.info("Reservation find success: ".concat(reservation.toString()));
        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }

    @GetMapping("/bookAvailable/{idBook}/")
    public ResponseEntity<BookAvailableDTO> searchBookAvailablePeriod(@PathVariable Long idBook
            , @RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        log.info("Receive request to verify book availability on period. idBook: "
                .concat(String.valueOf(idBook).concat(" startDate: ")
                        .concat(String.valueOf(startDate)).concat(" endDate: ")
                        .concat(String.valueOf(endDate))));
        return new ResponseEntity<>(reservationService.searchBookAvailableByPeriod(idBook, startDate, endDate), HttpStatus.OK);
    }

    @GetMapping("/booksAvailable/")
    public ResponseEntity<BookListDTO> searchBooksAvailableOnPeriod(@RequestParam LocalDate startDate,
                                                                    @RequestParam LocalDate endDate) {
        log.info("Receive request to search books available on period. "
                        .concat(String.valueOf(startDate)).concat(" ")
                        .concat(String.valueOf(endDate)));
        return new ResponseEntity<>(reservationService.searchBooksAvailableOnPeriod(startDate, endDate), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Reservation> saveReservation(@Valid @RequestBody ReservationDTO reservation) {
        log.info("Receive request to INSERT reservation: ".concat(reservation.toString()));
        return new ResponseEntity<>(reservationService.saveReservation(reservation), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable Long id,
                                                         @Valid @RequestBody ReservationDTO reservation) {
        log.info("Receive request to UPDATE reservation. id: ".concat(id.toString())
                .concat(" reservation: ".concat(reservation.toString())));
        return new ResponseEntity<>(reservationService.updateReservation(id, reservation), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Reservation> deleteReservation(@PathVariable Long id) {
        log.info("Receive request to DELETE reservation. id: ".concat(id.toString()));
        reservationService.deleteReservation(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
