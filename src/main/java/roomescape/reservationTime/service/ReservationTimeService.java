package roomescape.reservationTime.service;

import org.springframework.stereotype.Service;
import roomescape.reservationTime.domain.ReservationTime;
import roomescape.reservationTime.repository.ReservationTimeRepository;

import java.util.List;

@Service
public class ReservationTimeService {
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationTime> findAll() {
        return reservationTimeRepository.findAll();
    }

    public void deleteById(Long id) {
        reservationTimeRepository.deleteById(id);
    }

    public ReservationTime save(ReservationTime entity) {
        Long savedId = reservationTimeRepository.save(entity);
        return reservationTimeRepository.findById(savedId);
    }
}
