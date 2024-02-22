package neobis.travel.servises;

import neobis.travel.dto.*;

import java.util.List;

public interface TripService {
    SimpleResponse saveTrip(TripRequest tripRequest);
    SimpleResponse featured(Long tripId);
    SimpleResponse recommended(Long tripId);
    List<TripResponse> getByPopular();
    List<TripResponse> getByMostVisit();
    List<TripResponse> getByContinent(String continent);
    List<TripResponse> getTripByFeatured();
    List<TripResponse> getTripByRecommended();
    SimpleResponse bookingTrip(Long tripId, BookingRequest bookingRequest);
    TripResponse getCommentUser(Long tripId);
}
