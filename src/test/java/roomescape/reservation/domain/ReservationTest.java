package roomescape.reservation.domain;

import jakarta.persistence.EntityManager;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;
import roomescape.common.exception.RestApiException;
import roomescape.common.exception.status.ReservationErrorStatus;
import roomescape.reservationTime.domain.ReservationTime;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThatThrownBy;


class ReservationTest {

    @Test
    @DisplayName("예외: 지나간 날짜와 시간은 예약 불가능 하다.")
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