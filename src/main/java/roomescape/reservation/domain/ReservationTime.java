package roomescape.reservation.domain;

//            <th scope="col">순서</th>
//            <th scope="col">시간</th>

import java.time.LocalTime;

public class ReservationTime {
    private final LocalTime startTime;

    public ReservationTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getStartTime() {
        return startTime;
    }
}
