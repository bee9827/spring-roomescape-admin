package roomescape.reservation.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import roomescape.reservation.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationResponseDto(
        Long id,
        String name,
        @JsonFormat(pattern = "yyyy-MM-dd") LocalDate date,
        @JsonFormat(pattern = "HH:mm") LocalTime time) {

    public ReservationResponseDto(Reservation reservation) {
        this(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime().getStartAt()
        );
    }
}
