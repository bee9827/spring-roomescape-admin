package roomescape.reservation.domain;

import java.time.LocalDate;

/*
      <th>예약번호</th>
      <th>예약자</th>
      <th>날짜</th>
      <th>시간</th>
 */
public class Reservation {
    private Long id;
    private String name;
    private LocalDate date;
    private ReservationTime reservationTime;

    public Reservation(Long id, String name, LocalDate date, ReservationTime reservationTime) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.reservationTime = reservationTime;
    }

    protected void SetId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public ReservationTime getReservationTime() {
        return reservationTime;
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
