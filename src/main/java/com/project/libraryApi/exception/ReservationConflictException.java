package com.project.libraryApi.exception;

import com.project.libraryApi.dto.ReservationDTO;

public class ReservationConflictException extends RuntimeException {
    public ReservationConflictException(String message, ReservationDTO reservation) {
        super(message.concat(reservation.toString()));
    }

    public ReservationConflictException(String message) {
        super(message);
    }
}
