package roomescape.reservation.dto;


import roomescape.reservation.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationResponseDto(Long id, String name, LocalDate date, LocalTime time) {
    public ReservationResponseDto(Reservation reservation) {
        this(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getReservationTime().getStartTime()
        );
    }
}
