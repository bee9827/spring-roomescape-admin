package roomescape.reservationTime.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.common.CustomRepository;
import roomescape.reservationTime.domain.ReservationTime;

import javax.sql.DataSource;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ReservationTimeRepository implements CustomRepository<ReservationTime> {
    public static final RowMapper<ReservationTime> ROW_MAPPER = (resultSet, rowNum) -> new ReservationTime(
            resultSet.getLong("id"),
            resultSet.getTime("start_at").toLocalTime()
    );

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public ReservationTimeRepository(final JdbcTemplate jdbcTemplate, final DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Long save(final ReservationTime reservationTime) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("start_at", reservationTime.getStartAt());

        return jdbcInsert.executeAndReturnKey(parameters).longValue();
    }

    @Override
    public void deleteById(final Long id) {
        String sql = "DELETE FROM reservation_time WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public ReservationTime findById(final Long id) {
        String sql = "SELECT * FROM reservation_time WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, ROW_MAPPER, id);
    }

    @Override
    public List<ReservationTime> findAll() {
        String sql = "SELECT * FROM reservation_time";
        return jdbcTemplate.query(sql, ROW_MAPPER);
    }

    public Boolean existsByStartAt(LocalTime startAt) {
        String sql = "SELECT EXISTS (SELECT 1 FROM reservation_time WHERE start_at = ?)";

        return jdbcTemplate.queryForObject(sql, Boolean.class, startAt);
    }

    public ReservationTime findByStartAt(LocalTime startAt) {
        String sql = "SELECT * FROM reservation_time WHERE start_at = ?";

        return jdbcTemplate.queryForObject(sql, ROW_MAPPER, startAt);
    }
}
