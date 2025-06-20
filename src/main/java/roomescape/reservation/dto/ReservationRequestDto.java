package roomescape.reservation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import roomescape.reservation.domain.Reservation;
import roomescape.reservationTime.domain.ReservationTime;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationRequestDto(
        @NotNull
        @NotBlank(message = "이름은 공백일 수 없습니다.")
        String name,

        @NotNull(message = "날짜는 공백일 수 없습니다.")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate date,

        @NotNull(message = "시간은 공백일 수 없습니다.")
        @DateTimeFormat(pattern = "HH:mm")
        LocalTime time) {
    public Reservation toEntity(ReservationTime reservationTime) {
        return new Reservation.Builder()
                .name(name)
                .date(date)
                .reservationTime(reservationTime)
                .build();
    }
}
