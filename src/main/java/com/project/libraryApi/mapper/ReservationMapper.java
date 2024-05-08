package com.project.libraryApi.mapper;

import com.project.libraryApi.dto.ReservationDTO;
import com.project.libraryApi.model.Reservation;

public class ReservationMapper {

    public static Reservation toReservation(Reservation originalReservation, ReservationDTO newReservation) {
        originalReservation.setIdBook(newReservation.getIdBook());
        originalReservation.setIdUser(newReservation.getIdUser());
        originalReservation.setStartDate(newReservation.getStartDate());
        originalReservation.setEndDate(newReservation.getEndDate());
        return originalReservation;
    }

    public static Reservation toReservation(ReservationDTO newReservation) {
        return new Reservation(newReservation.getIdUser(),
                newReservation.getIdBook(),
                newReservation.getStartDate(),
                newReservation.getEndDate());
    }
}
