package roomescape.reservation.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import roomescape.reservation.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationResponseDto(
        Long id,
        String name,
        @JsonFormat(pattern = DATE_PATTERN) LocalDate date,
        @JsonFormat(pattern = TIME_PATTERN) LocalTime time) {
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String TIME_PATTERN = "HH:mm";


    public ReservationResponseDto(Reservation reservation) {
        this(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime().getStartAt()
        );
    }
}
