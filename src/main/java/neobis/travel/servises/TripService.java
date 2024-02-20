package neobis.travel.servises;

import neobis.travel.dto.*;
import neobis.travel.enums.Continent;

import java.util.List;

public interface TripService {
    SimpleResponse saveTrip(TripRequest tripRequest, Continent continent);
    SimpleResponse featured(Long tripId);
    SimpleResponse recommended(Long tripId);
    List<TripResponse> getByPopular();
    List<TripResponse> getByMostVisit();
    List<TripResponse> getByContinent(Continent continent);
    List<TripResponse> getTripByFeatured();
    List<TripResponse> getTripByRecommended();
    SimpleResponse bookingTrip(Long tripId, boolean reserveAnonymous, BookingRequest bookingRequest);
    TripResponse getCommentUser(Long tripId);
}
