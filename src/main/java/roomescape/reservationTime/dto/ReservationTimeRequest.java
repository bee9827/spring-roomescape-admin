package roomescape.reservationTime.dto;

import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import roomescape.reservationTime.domain.ReservationTime;

import java.time.LocalTime;

public record ReservationTimeRequest(
        Long id,

        @NotNull
        @DateTimeFormat(pattern = "HH:mm")
        LocalTime startAt
        ) {

        public ReservationTime toEntity() {
                return new ReservationTime(
                        id,
                        startAt
                );
        }
}
