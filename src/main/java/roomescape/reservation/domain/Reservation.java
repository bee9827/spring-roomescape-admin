package roomescape.reservation.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import roomescape.common.exception.RestApiException;
import roomescape.common.exception.status.ReservationErrorStatus;
import roomescape.reservationTime.domain.ReservationTime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(
        name = "RESERVATION",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "RESERVATION_DATE",
                        columnNames = {
                                "date",
                                "time_id"
                })
        }
)
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //쓰레드 세이프 하지 않다.
    private Long id;
    private String name;

    private LocalDate date;

    @ManyToOne
    private ReservationTime time;

    protected Reservation() {
    }

    public Reservation(Long id, String name, LocalDate date, ReservationTime time) {
        validDateTime(date,time.getStartAt());
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public ReservationTime getTime() {
        return time;
    }

    private void validDateTime(LocalDate date, LocalTime startAt) {
        LocalDateTime now = LocalDateTime.now();

        if(now.isAfter(LocalDateTime.of(date, startAt))){
            throw new RestApiException(ReservationErrorStatus.PAST_TIME);
        }
    }

    public static class Builder {
        private Long id;
        private String name;
        private LocalDate date;
        private ReservationTime reservationTime;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder date(LocalDate date) {
            this.date = date;
            return this;
        }

        public Builder reservationTime(ReservationTime reservationTime) {
            this.reservationTime = reservationTime;
            return this;
        }

        public Reservation build() {
            return new Reservation(id, name, date, reservationTime);
        }
    }
}
