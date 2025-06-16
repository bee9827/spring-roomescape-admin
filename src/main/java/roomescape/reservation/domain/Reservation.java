package roomescape.reservation.domain;

import jakarta.persistence.*;
import roomescape.reservationTime.domain.ReservationTime;

import java.time.LocalDate;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //쓰레드 세이프 하지 않다.
    private Long id;
    private String name;
    private LocalDate date;

    @OneToOne
    private ReservationTime time;

    protected Reservation() {
    }

    public Reservation(Long id, String name, LocalDate date, ReservationTime time) {
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
