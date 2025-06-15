package roomescape.reservation.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.domain.ReservationTime;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class ReservationRepositoryTest {

    private ReservationRepository reservationRepository;

    @Test
    void findAll() {
        reservationRepository = new ReservationRepository(ReservationRepository.WithDefaultValues());
        List<Reservation> reservations = reservationRepository.findAll();

        assertThat(reservations.size()).isEqualTo(3);
    }

    @Test
    void findById() {
        reservationRepository = new ReservationRepository(ReservationRepository.WithDefaultValues());
        Reservation reservation = reservationRepository.findById(1L);
        assertThat(reservation).isNotNull();
        assertThat(reservation.getId()).isEqualTo(1L);
    }

    @DisplayName("save: Id 값을 넣지 않아도 저장에 성공한다.")
    @Test
    void save() {
        reservationRepository = new ReservationRepository(new CopyOnWriteArraySet<>());

        Reservation reservation = new Reservation.Builder()
                .name("이름1")
                .date(LocalDate.of(2020, 1, 1))
                .reservationTime(new ReservationTime(LocalTime.of(1, 0)))
                .build();

        Reservation savedReservation = reservationRepository.save(reservation);

        assertThat(savedReservation)
                .isNotNull()
                .isEqualTo(reservation);

        //
        assertThat(savedReservation.getId()).isEqualTo(1);
    }

    @DisplayName("deleteById: 삭제에 성공한다.")
    @Test
    void deleteById() {
        reservationRepository = new ReservationRepository(ReservationRepository.WithDefaultValues());

        reservationRepository.deleteById(1L);

        assertThat(reservationRepository.findAll())
                .size()
                .isEqualTo(2);
    }

    @DisplayName("deleteById: 없는 아이디가 들어오면 예외를 발생시킨다.")
    @Test
    void deleteByIdException() {
        reservationRepository = new ReservationRepository(ReservationRepository.WithDefaultValues());

        assertThatThrownBy(() -> reservationRepository.deleteById(0L))
                .isInstanceOf(IllegalArgumentException.class);
    }
}