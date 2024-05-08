package com.project.libraryApi.repository;

import com.project.libraryApi.model.Book;
import com.project.libraryApi.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query(value = "select r from Reservation r where r.idBook = :idBook and ((r.startDate BETWEEN :startDate AND :endDate) " +
            "OR (r.endDate BETWEEN :startDate AND :endDate))")
    List<Reservation> getAllReservationsByBookBetweenDates(@Param("idBook") Long idBook,
                                                           @Param("startDate") LocalDate startDate,
                                                           @Param("endDate") LocalDate endDate);

//    @Query(value = "select b.id, b.title from books b where b.id not in (select r.id_book from reservations r " +
//            "where (r.start_date BETWEEN :startDate AND :endDate) and(r.end_date BETWEEN :startDate AND :endDate) )",
//            nativeQuery = true)

    @Query(value = "select b from Book b where b.id not in (select r.idBook from Reservation r " +
            "where (r.startDate BETWEEN :startDate AND :endDate) and(r.endDate BETWEEN :startDate AND :endDate) )")
    List<Book> searchAvailableBooksOnPeriod(@Param("startDate") LocalDate startDate,
                                            @Param("endDate") LocalDate endDate);



}
