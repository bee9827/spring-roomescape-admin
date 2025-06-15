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
    private final AtomicLong atomicLong = new AtomicLong();
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
        reservation.setId(atomicLong.incrementAndGet());
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
        reservations.add(new Reservation(1L, "브라운", LocalDate.of(2023, 1, 1), new ReservationTime(LocalTime.of(10, 0))));
        reservations.add(new Reservation(2L, "브라운", LocalDate.of(2023, 6, 2), new ReservationTime(LocalTime.of(11, 0))));

        return reservations;
    }
}
