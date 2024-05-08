package com.project.libraryApi.service;

import com.project.libraryApi.dto.BookAvailableDTO;
import com.project.libraryApi.dto.BookListDTO;
import com.project.libraryApi.dto.ReservationDTO;
import com.project.libraryApi.exception.ReservationConflictException;
import com.project.libraryApi.exception.ReservationNotFoundException;
import com.project.libraryApi.mapper.ReservationMapper;
import com.project.libraryApi.model.Book;
import com.project.libraryApi.model.Reservation;
import com.project.libraryApi.repository.ReservationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@CacheConfig(cacheNames = {"reservations"})
public class ReservationService {
    Logger log = LoggerFactory.getLogger(ReservationService.class);
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;

    @Cacheable("reservationsList")
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Cacheable(key = "#id", unless = "#result == null")
    public Reservation getReservationById(Long id) {
        log.info("Finding reservation. id: ".concat(id.toString()));
        return reservationRepository
                .findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(id));
    }

    @Caching(evict = @CacheEvict(cacheNames = "reservationsList", allEntries = true),
            put = @CachePut(key = "#result.id", unless = "#result == null"))
    public Reservation saveReservation(ReservationDTO reservation) {
        log.info("Saving reservation. ".concat(reservation.toString()));
        if (validateReservation(reservation)) {
            Reservation saveReservation = reservationRepository.save(ReservationMapper.toReservation(reservation));
            log.info("Reservation saved success: ".concat(saveReservation.toString()));
            return saveReservation;
        }
        return null;
    }

    public BookAvailableDTO searchBookAvailableByPeriod(Long idBook, LocalDate startDate, LocalDate endDate) {
        BookAvailableDTO availableDTO = new BookAvailableDTO(getBookReservationsByPeriod(idBook, startDate, endDate)
                .isEmpty());
        log.info("Book is available on period: ".concat(availableDTO.toString()));
        return availableDTO;
    }

    public List<Reservation> getBookReservationsByPeriod(Long idBook, LocalDate startDate, LocalDate endDate) {
        validateDatesReservation(startDate, endDate);
        return reservationRepository.getAllReservationsByBookBetweenDates(idBook, startDate, endDate);
    }

    private boolean validateReservation(ReservationDTO reservation) {
        validateDatesReservation(reservation.getStartDate(), reservation.getEndDate());
        if (!getBookReservationsByPeriod(reservation.getIdBook(), reservation.getStartDate(), reservation.getEndDate())
                .isEmpty()) {
            throw new ReservationConflictException("Already has a reservation for this book in this period. ", reservation);
        }

        return bookService.getBookById(reservation.getIdBook()) != null
                && userService.getUserById(reservation.getIdUser()) != null;
    }

    private void validateDatesReservation(LocalDate startDate, LocalDate endDate) {
        if (endDate.isBefore(startDate)) {
            throw new ReservationConflictException("StartDate must be BEFORE EndDate. startDate: "
                    .concat(String.valueOf(startDate)).concat(" endDate: ")
                    .concat(String.valueOf(endDate)));
        }
        if (startDate.isBefore(LocalDate.now())) {
            throw new ReservationConflictException("StartDate must be in the future. ".concat(String.valueOf(startDate)));
        }
    }

    @CacheEvict(key = "#id")
    @Caching(evict = {@CacheEvict(cacheNames = "reservationsList", allEntries = true), @CacheEvict(key = "#id")})
    public void deleteReservation(Long id) {
        log.info("Deleting reservation. id: ".concat(id.toString()));
        reservationRepository.deleteById(id);
        log.info("Reservation deleted success: id ".concat(id.toString()));
    }

    @Caching(evict = @CacheEvict(cacheNames = "reservationsList", allEntries = true),
            put = @CachePut(key = "#result.id", unless = "#result == null"))
    public Reservation updateReservation(Long id, ReservationDTO reservation) {
        log.info("Updating reservation. id: ".concat(id.toString()).concat(" new data: ".concat(reservation.toString())));
        Reservation originalReservation = reservationRepository
                .findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(id));
        if (validateReservation(reservation)) {
            Reservation saveReservation = reservationRepository.save(ReservationMapper
                    .toReservation(originalReservation, reservation));
            log.info("Reservation updated success: id ".concat(id.toString()));
            return saveReservation;
        }
        return originalReservation;
    }

    public BookListDTO searchBooksAvailableOnPeriod(LocalDate startDate, LocalDate endDate) {
        validateDatesReservation(startDate, endDate);
        List<Book> bookList = reservationRepository.searchAvailableBooksOnPeriod(startDate, endDate);
        log.info("Books available on period: ".concat(bookList.toString()));
        return new BookListDTO(bookList);
    }
}
