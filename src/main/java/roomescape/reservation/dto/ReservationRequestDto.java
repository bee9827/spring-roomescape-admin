package roomescape.reservation.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.domain.ReservationTime;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationRequestDto(
        @NotBlank
        String name,

        @NotNull
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate date,

        @NotNull
        @DateTimeFormat(pattern = "HH:mm")
        LocalTime time) {
    public Reservation toEntity() {
        return new Reservation.Builder()
                .name(name)
                .date(date)
                .reservationTime(new ReservationTime(time))
                .build();
    }
}
