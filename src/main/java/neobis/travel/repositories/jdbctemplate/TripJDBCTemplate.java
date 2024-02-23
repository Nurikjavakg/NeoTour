package neobis.travel.repositories.jdbctemplate;

import lombok.RequiredArgsConstructor;
import neobis.travel.dto.TripResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TripJDBCTemplate {

    private final JdbcTemplate jdbcTemplate;

    public TripResponse getAllTrips(ResultSet rs, int rowName) throws SQLException {
        return new TripResponse(
                rs.getLong("tripId"),
                rs.getString("tripName"),
                rs.getString("tripImage"));
    }

    public List<TripResponse> getBookingTrips(String email) {
        String sql = """
                               
                SELECT
                   t.trip_id AS tripId,
                   t.name AS tripName,
                   t.trip_image AS tripImage
                               FROM
                   users u
                       left join trips t on t.reservoir_user_id= u.user_id
                                where u.email = ?;
                """;
        return jdbcTemplate.query(sql, this::getAllTrips, email);
    }

    public List<TripResponse> getFeaturedTrips(String email) {
        String sql = """
                SELECT
           t.trip_id AS tripId,
           t.name AS tripName,
           t.trip_image AS tripImage
        FROM
        users u
        left join trips t on t.featured_user_user_id= u.user_id
        where u.email = ?""";
         return jdbcTemplate.query(sql, this::getAllTrips, email);
     }
}