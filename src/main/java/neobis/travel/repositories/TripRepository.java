package neobis.travel.repositories;

import neobis.travel.dto.TripResponse;
import neobis.travel.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    @Query("select new neobis.travel.dto.TripResponse(t.tripId, t.name, t.tripImage) from Trip t where t.popular = true")
    List<TripResponse> getTripByPopular();
    @Query("select new neobis.travel.dto.TripResponse(t.tripId, t.name, t.tripImage) from Trip t where t.mostVisited = true")
    List<TripResponse> getTripByMostVisit();
    @Query("select new neobis.travel.dto.TripResponse(t.tripId, t.name, t.tripImage) from Trip t where t.continent = ?1")
    List<TripResponse> getTripByContinent(String continent);
    Optional<Trip> getTripByTripId(Long tripId);
    @Query("select new neobis.travel.dto.TripResponse(t.tripId, t.name, t.tripImage) from Trip t where t.featured = true")
    List<TripResponse> getTripByFeatured();
    @Query("select new neobis.travel.dto.TripResponse(t.tripId, t.name, t.tripImage) from Trip t where t.recommended = true")
    List<TripResponse> getTripByRecommended();
    @Query("select new neobis.travel.dto.TripResponse(t.tripId, t.name, t.tripImage, t.description, t.place) from Trip t where t.tripId = ?1")
    Optional<TripResponse> getTripByIdComments(Long tripId);
}