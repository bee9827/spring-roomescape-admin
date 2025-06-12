package roomescape.reservation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.ReservationResponseDto;
import roomescape.reservation.repository.Repository;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationApiController {

    private final Repository<Reservation> reservationRepository;

    public ReservationApiController(Repository<Reservation> reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @RequestMapping
    public ResponseEntity<List<ReservationResponseDto>> getReservations() {
        List<ReservationResponseDto> reservations = reservationRepository.findAll()
                .stream()
                .map(ReservationResponseDto::new)
                .toList();

        return ResponseEntity.ok(reservations);
    }
}
