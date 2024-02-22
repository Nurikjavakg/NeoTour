package neobis.travel.servises.servicesImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neobis.travel.dto.*;
import neobis.travel.entity.Trip;
import neobis.travel.entity.User;
import neobis.travel.enums.BookingStatus;
import neobis.travel.exceptions.NotFoundException;
import neobis.travel.repositories.TripRepository;
import neobis.travel.repositories.UserRepository;
import neobis.travel.servises.TripService;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
@Slf4j
public class TripServiceImpl implements TripService {

    private final TripRepository tripRepository;
    private final UserRepository userRepository;
    private final JdbcTemplate jdbcTemplate;


    public User getAuthFromUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        return userRepository.getUserByEmail(email).orElseThrow(
                () -> {
                    log.info("User with email: " + email + " not found!");
                    return new NotFoundException(String.format("Пользователь с адресом электронной почты: %s не найден!", email));
                });
    }

    @Override
    public SimpleResponse saveTrip(TripRequest tripRequest) {
        Trip trip = new Trip();
        trip.setName(tripRequest.getName());
        trip.setTripImage(tripRequest.getTripImage());
        trip.setDescription(tripRequest.getDescription());
        trip.setPlace(tripRequest.getPlace());
        trip.setContinent(tripRequest.getContinent());
        trip.setPopular(tripRequest.isPopular());
        trip.setMostVisited(tripRequest.isMostVisited());
        tripRepository.save(trip);
        log.info("Trip is successfully saved");
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Trip is successfully saved")
                .build();
    }

    @Override
    public SimpleResponse featured(Long tripId) {
        Trip trip = tripRepository.getTripByTripId(tripId).
                orElseThrow(()-> new NotFoundException("Trip with Id:"+tripId+" not found!"));
        trip.setFeatured(true);
        tripRepository.save(trip);
        log.info("Trip is featured");
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Trip is featured")
                .build();
    }

    @Override
    public SimpleResponse recommended(Long tripId) {
        Trip trip = tripRepository.getTripByTripId(tripId).
                orElseThrow(()-> new NotFoundException("Trip with Id:"+tripId+" not found!"));
        trip.setRecommended(true);
        tripRepository.save(trip);
        log.info("Trip is recommended");
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Trip recommended")
                .build();
    }

    @Override
    public List<TripResponse> getByPopular() {
        return tripRepository.getTripByPopular();

    }

    @Override
    public List<TripResponse> getByMostVisit() {
        return tripRepository.getTripByMostVisit();
    }

    @Override
    public List<TripResponse> getByContinent(String continent) {
        return tripRepository.getTripByContinent(continent);
    }

    @Override
    public List<TripResponse> getTripByFeatured() {
        return tripRepository.getTripByFeatured();
    }

    @Override
    public List<TripResponse> getTripByRecommended() {
        return tripRepository.getTripByRecommended();
    }

    @Override
    public SimpleResponse bookingTrip(Long tripId, BookingRequest bookingRequest) {
        User user = getAuthFromUser();
        Trip trip = tripRepository.getTripByTripId(tripId).
                orElseThrow(()-> new NotFoundException("Trip with Id:"+tripId+" not found!"));

            trip.setReservoir(user);
            trip.setWishesToTrip(bookingRequest.getWishesToTrip());
            user.setPhoneNumber(bookingRequest.getPhoneNumber());
            user.setUserSum(bookingRequest.getUserSum());
            trip.setBookingStatus(BookingStatus.RESERVED);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Тур успешно забронировано")
                .build();
    }

   @Override
    public TripResponse getCommentUser(Long tripId) {
       TripResponse tripResponse = tripRepository.getTripByIdComments(tripId)
               .orElseThrow(() -> new NotFoundException("Not found"));

       String sql = "SELECT u.user_image, CONCAT(u.first_name, ' ', u.last_name) as full_name, c.comment\n" +
               "FROM comment c join public.users u on u.user_id = c.user_user_id\n" +
               "join trips t on t.trip_id = c.trips_trip_id\n" +
               "WHERE t.trip_id = ?";

       List<CommentResponse> commentResponse = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CommentResponse.class), tripId);

       tripResponse.setCommentResponse(commentResponse);
       return tripResponse;
   }
}