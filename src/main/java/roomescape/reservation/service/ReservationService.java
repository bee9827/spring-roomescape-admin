package roomescape.reservation.service;

import org.springframework.stereotype.Service;
import roomescape.common.exception.RestApiException;
import roomescape.common.exception.status.ReservationErrorStatus;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.ReservationRequestDto;
import roomescape.reservation.repository.ReservationRepository;
import roomescape.reservationTime.domain.ReservationTime;
import roomescape.reservationTime.repository.ReservationTimeRepository;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;


    public ReservationService(ReservationRepository reservationRepository, ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public Reservation save(ReservationRequestDto reservationRequestDto) {
        if (!reservationTimeRepository.existsByStartAt(reservationRequestDto.time())) {
            throw new RestApiException(ReservationErrorStatus.TIME_NOT_FOUND);
        }
        ReservationTime time = reservationTimeRepository.findByStartAt(reservationRequestDto.time());
        Reservation requestReservation = reservationRequestDto.toEntity(time);

        Long savedId = reservationRepository.save(requestReservation);
        return reservationRepository.findById(savedId);
    }

    public Reservation findById(Long id) {
        if(!reservationRepository.existsById(id))
            throw new RestApiException(ReservationErrorStatus.NOT_FOUND);
        return reservationRepository.findById(id);
    }

    public List<Reservation> findAll() {
        return reservationRepository.findAll()
                .stream()
                .toList();
    }

    public void deleteById(Long id) {
        if (!reservationRepository.existsById(id)) {
            throw new RestApiException(ReservationErrorStatus.NOT_FOUND);
        }

        reservationRepository.deleteById(id);
    }
}
