package roomescape.reservation.domain;

import jakarta.persistence.EntityManager;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import roomescape.common.exception.RestApiException;
import roomescape.common.exception.status.ReservationErrorStatus;
import roomescape.reservationTime.domain.ReservationTime;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class ReservationTest {
    private final EntityManager em;

    @Autowired
    public ReservationTest(EntityManager em) {
        this.em = em;
    }

    @Test
    @DisplayName("DUPLICATE_EXCEPTION: 중복된 날짜와 시간이 저장 되면 예외를 던진다.")
    @Transactional
    public void duplicateDateException() {
        ReservationTime reservationTime = new ReservationTime(LocalTime.of(10, 0));

        Reservation reservation = new Reservation.Builder()
                .name("테스트")
                .date(LocalDate.now().plusDays(1))
                .reservationTime(reservationTime)
                .build();
        Reservation duplicateReservation = new Reservation.Builder()
                .name("테스트2")
                .date(LocalDate.now().plusDays(1))
                .reservationTime(reservationTime)
                .build();
        em.persist(reservationTime);
        em.persist(reservation);
        assertThatThrownBy(() -> em.persist(duplicateReservation))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    @DisplayName("PAST_TIME_EXCEPTION: 지나간 날짜와 시간은 예약 불가능 하다.")
    public void presentOrFuture() {
        ReservationTime reservationTime = new ReservationTime(LocalTime.now());

        assertThatThrownBy(
                () -> new Reservation.Builder()
                        .name("테스트")
                        .date(LocalDate.now())
                        .reservationTime(reservationTime)
                        .build())

                .isInstanceOf(RestApiException.class)
                .hasMessage(ReservationErrorStatus.PAST_TIME.getMessage());
    }

}