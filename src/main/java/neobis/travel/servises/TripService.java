package neobis.travel.servises;

import neobis.travel.dto.*;

import java.util.List;

public interface TripService {
    SimpleResponse saveTrip(TripRequest tripRequest);
    SimpleResponse featured(Long tripId);
    SimpleResponse recommended(Long tripId);
    List<TripResponse> getByPopular();
    List<TripResponse> getByMostVisit();
    List<TripResponse> getAsiaTrips();
    List<TripResponse> getEuropeTrips();
    List<TripResponse> getTripByFeatured();
    PaginationResponse getTripByRecommended(int currentPage, int pageSize);
    SimpleResponse bookingTrip(Long tripId, BookingRequest bookingRequest);
    TripResponse getTripById(Long tripId);
}
