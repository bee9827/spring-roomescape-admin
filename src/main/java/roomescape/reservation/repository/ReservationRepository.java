package roomescape.reservation.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.common.CustomRepository;
import roomescape.reservation.domain.Reservation;
import roomescape.reservationTime.domain.ReservationTime;

import javax.sql.DataSource;
import java.sql.Date;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class ReservationRepository implements CustomRepository<Reservation> {


    private static final RowMapper<Reservation> ROW_MAPPER = (resultSet, rowNum) -> new Reservation(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            resultSet.getDate("date").toLocalDate(),
            new ReservationTime(
                    resultSet.getLong("time_id"),
                    resultSet.getTime("start_at").toLocalTime()
            )
    );

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public ReservationRepository(final JdbcTemplate jdbcTemplate, final DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Long save(final Reservation reservation) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", reservation.getName());
        parameters.put("date", Date.valueOf(reservation.getDate()));
        parameters.put("time_id", reservation.getTime().getId());

        return jdbcInsert.executeAndReturnKey(parameters).longValue();
    }

    @Override
    public Reservation findById(final Long id) {
        String sql = """               
                SELECT
                    r.id as reservation_id,
                    r.name,
                    r.date,
                    t.id as time_id,
                    t.start_at as time_value
                FROM reservation as r
                INNER JOIN reservation_time as t
                ON r.time_id = t.id
                WHERE r.id = ?
                """;
        return jdbcTemplate.queryForObject(sql, ROW_MAPPER, id);
    }

    @Override
    public void deleteById(final Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Collection<Reservation> findAll() {
        String sql = """               
                SELECT
                    r.id as reservation_id,
                    r.name,
                    r.date,
                    t.id as time_id,
                    t.start_at as time_value
                FROM reservation as r
                INNER JOIN reservation_time as t
                ON r.time_id = t.id
                """;

        return jdbcTemplate.query(sql, ROW_MAPPER);
    }
}