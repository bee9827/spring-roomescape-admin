package roomescape.reservation.repository;

import org.springframework.stereotype.Component;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.domain.ReservationTime;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class ReservationRepository implements Repository<Reservation> {
    private final AtomicLong id = new AtomicLong(1L);
    private final Set<Reservation> reservations;

    public ReservationRepository() {
        this.reservations = WithDefaultValues();
    }

    public ReservationRepository(Set<Reservation> reservations) {
        this.reservations = reservations;
    }

    @Override
    public List<Reservation> findAll() {
        return new ArrayList<>(reservations);
    }

    @Override
    public Reservation findById(long id) {
        return reservations.stream()
                .filter(r -> r.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Reservation with id " + id + " not found"));

    }

    @Override
    public Reservation save(Reservation reservation) {
        reservations.add(reservation);
        return reservation;
    }

    @Override
    public void deleteById(Long id) {
        Reservation savedReservation = findById(id);
        reservations.remove(savedReservation);
    }

    public static Set<Reservation> WithDefaultValues() {
        Set<Reservation> reservations = new CopyOnWriteArraySet<>();
        reservations.add(new Reservation(1L, "이름1", LocalDate.of(2025, 6, 1), new ReservationTime(LocalTime.of(1, 0))));
        reservations.add(new Reservation(2L, "이름2", LocalDate.of(2025, 6, 2), new ReservationTime(LocalTime.of(2, 0))));
        reservations.add(new Reservation(3L, "이름3", LocalDate.of(2025, 6, 3), new ReservationTime(LocalTime.of(3, 0))));

        return reservations;
    }
}
