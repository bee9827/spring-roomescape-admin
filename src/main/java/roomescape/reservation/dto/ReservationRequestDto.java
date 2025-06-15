package roomescape.reservation.dto;

import roomescape.reservation.domain.Reservation;
import roomescape.reservation.domain.ReservationTime;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationRequestDto(String name, LocalDate date, LocalTime time) {
    public Reservation toEntity(){
        return new Reservation.Builder()
                .name(name)
                .date(date)
                .reservationTime(new ReservationTime(time))
                .build();
    }
}
