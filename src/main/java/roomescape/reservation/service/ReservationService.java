package roomescape.reservation.service;

import org.springframework.stereotype.Service;
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
        ReservationTime time = reservationTimeRepository.findByStartAt(reservationRequestDto.time());
        if(time == null) {
            throw new IllegalArgumentException("Time not found");
        }

        Reservation requestReservation = reservationRequestDto.toEntity(time);

        Long savedId = reservationRepository.save(requestReservation);
        return reservationRepository.findById(savedId);
    }

    public Reservation findById(Long id) {
        return reservationRepository.findById(id);
    }

    public List<Reservation> findAll() {
        return reservationRepository.findAll()
                .stream()
                .toList();
    }

    public void deleteById(Long id) {
        //no content 예외처리
        reservationRepository.deleteById(id);
    }
}
