package roomescape.reservation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.ReservationRequestDto;
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

    @GetMapping
    public ResponseEntity<List<ReservationResponseDto>> getReservations() {
        List<ReservationResponseDto> reservations = reservationRepository.findAll()
                .stream()
                .map(ReservationResponseDto::new)
                .toList();

        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponseDto> getReservation(@PathVariable Long id) {
        ReservationResponseDto reservation = new ReservationResponseDto(reservationRepository.findById(id));

        return ResponseEntity.ok(reservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationRepository.deleteById(id);

        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<ReservationResponseDto> createReservation(@RequestBody ReservationRequestDto reservationRequestDto) {
        Reservation reservation = reservationRepository.save(reservationRequestDto.toEntity());

        return ResponseEntity.ok(new ReservationResponseDto(reservation));
    }
}
