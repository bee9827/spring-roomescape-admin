package roomescape.reservation.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import roomescape.common.exception.RestApiException;
import roomescape.common.exception.status.ReservationErrorStatus;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.ReservationRequestDto;
import roomescape.reservation.repository.ReservationRepository;
import roomescape.reservationTime.domain.ReservationTime;
import roomescape.reservationTime.repository.ReservationTimeRepository;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {
    @Mock
    ReservationRepository reservationRepository;
    @Mock
    ReservationTimeRepository reservationTimeRepository;

    @InjectMocks
    ReservationService reservationService;

    ReservationTime reservationTime = getReservationTime();
    ReservationRequestDto reservationRequestDto = getReservationReqeustDto();
    Reservation reservation = createReservation(reservationTime);

    private Reservation createReservation(ReservationTime reservationTime) {
        return new Reservation.Builder()
                .id(1L)
                .name("테스트")
                .date(LocalDate.now().plusDays(1))
                .reservationTime(reservationTime)
                .build();
    }

    private ReservationTime getReservationTime() {
        return new ReservationTime(
                1L,
                LocalTime.of(10, 0)
        );
    }

    private ReservationRequestDto getReservationReqeustDto() {
        return new ReservationRequestDto(
                "테스트",
                LocalDate.now().plusDays(1),
                LocalTime.of(10, 0)
        );
    }

    @Nested
    @DisplayName("save")
    class Save {

        @DisplayName("save: 저장에 성공한다.")
        @Test
        void save() {
            when(reservationTimeRepository.existsByStartAt(reservationRequestDto.time())).thenReturn(true);
            when(reservationTimeRepository.findByStartAt(any(LocalTime.class))).thenReturn(reservationTime);
            when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation.getId());
            when(reservationRepository.findById(any(Long.class))).thenReturn(reservation);

            Reservation savedReservation = reservationService.save(reservationRequestDto);

            assertThat(savedReservation).isEqualTo(reservation);
        }

        @Test
        @DisplayName("예외: 저장된 Time이 없다면 예외를 던진다.")
        void reservationTimeNotFound() {
            when(
                    reservationTimeRepository.existsByStartAt(
                            any(LocalTime.class))
            ).thenReturn(false);

            assertThatThrownBy(() -> reservationService.save(reservationRequestDto))
                    .isInstanceOf(RestApiException.class)
                    .hasMessage(ReservationErrorStatus.TIME_NOT_FOUND.getMessage());
        }

        @Test
        @DisplayName("예외: 중복된 날짜와 시간이 저장 되면 예외를 던진다.")
        public void duplicateDateException() {
            when(reservationTimeRepository.existsByStartAt(any()))
                    .thenReturn(true);
            when(reservationRepository.existsByDateAndTimeId(any(), any()))
                    .thenReturn(true);
            when(reservationTimeRepository.findByStartAt(any())).thenReturn(reservationTime);

            assertThatThrownBy(() -> reservationService.save(reservationRequestDto))
                    .isInstanceOf(RestApiException.class)
                    .hasMessage(ReservationErrorStatus.DUPLICATE_DATE_TIME.getMessage());
        }

//        @Test
//        @DisplayName("예외: 날짜와 시간이 중복 됐다면 예외를 던진다.") -> 저장소의 역할
//        void reservationDuplicateException() {
//            when(reservationTimeRepository.existsByStartAt(reservationRequestDto.time())).thenReturn(true);
//
//
//        }
    }

    @Nested
    @DisplayName("findById")
    class FindById {
        @Test
        @DisplayName("조회에 성공한다")
        void findById() {
            when(reservationRepository.existsById(1L)).thenReturn(true);
            when(reservationRepository.findById(any())).thenReturn(reservation);

            Reservation request = reservationService.findById(1L);
            assertThat(request).isEqualTo(reservation);
        }

        @Test
        @DisplayName("예외: 없는 아이디 라면 예외를 던진다.")
        void findByIdNotFound() {
            when(reservationRepository.existsById(any())).thenReturn(false);

            assertThatThrownBy(() -> reservationService.findById(1L))
                    .isInstanceOf(RestApiException.class)
                    .hasMessage(ReservationErrorStatus.NOT_FOUND.getMessage());
        }
    }

    @Nested
    @DisplayName("deleteById: ")
    class deleteById {
        @Test
        @DisplayName("삭제에 성공한다")
        void success() {
            when(reservationRepository.existsById(any())).thenReturn(true);
            reservationService.deleteById(1L);
            when(reservationRepository.existsById(any())).thenReturn(false);

            assertThat(reservationRepository.existsById(1L)).isFalse();
        }

        @Test
        @DisplayName("예외: 없는 아이디 라면 예외를 던진다.")
        void notFoundException() {
            when(reservationRepository.existsById(any())).thenReturn(false);

            assertThatThrownBy(() -> reservationService.deleteById(0L))
                    .isInstanceOf(RestApiException.class)
                    .hasMessage(ReservationErrorStatus.NOT_FOUND.getMessage());
        }
    }
}