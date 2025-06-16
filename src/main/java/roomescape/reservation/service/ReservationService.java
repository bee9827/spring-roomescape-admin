package roomescape.reservation.service;

import org.springframework.stereotype.Service;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.repository.ReservationRepository;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Reservation save(Reservation reservation) {
        //동일한 요청이 두번 들어 올 경우 어떻게 할지 생각해 봐야함.
        Long savedId = reservationRepository.save(reservation);
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
