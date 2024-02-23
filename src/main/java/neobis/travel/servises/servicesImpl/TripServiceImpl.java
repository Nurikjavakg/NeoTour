package neobis.travel.servises.servicesImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neobis.travel.dto.*;
import neobis.travel.entity.Trip;
import neobis.travel.entity.User;
import neobis.travel.enums.BookingStatus;
import neobis.travel.exceptions.BadCredentialException;
import neobis.travel.exceptions.NotFoundException;
import neobis.travel.repositories.TripRepository;
import neobis.travel.repositories.UserRepository;
import neobis.travel.repositories.jdbctemplate.TripJDBCTemplate;
import neobis.travel.servises.TripService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
@Slf4j
public class TripServiceImpl implements TripService {

    private final TripRepository tripRepository;
    private final UserRepository userRepository;
    private final JdbcTemplate jdbcTemplate;
    private final TripJDBCTemplate tripJDBCTemplate;


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
        User user = getAuthFromUser();

        Trip trip = tripRepository.getTripByTripId(tripId).
                orElseThrow(()-> new NotFoundException("Trip with Id:"+tripId+" not found!"));
        trip.setFeatured(true);
        trip.setFeaturedUser(user);
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
    public List<TripResponse> getAsiaTrips() {
        return tripRepository.getAsiaTrips();
    }

    @Override
    public List<TripResponse> getEuropeTrips() {
        return tripRepository.getEuropeTrips();
    }

    @Override
    public List<TripResponse> getTripByFeatured() {
        User user = getAuthFromUser();
        return tripJDBCTemplate.getFeaturedTrips(user.getEmail());
    }

    @Override
    public PaginationResponse getTripByRecommended(int currentPage, int pageSize) {
        Pageable pageable = PageRequest.of(currentPage-1,pageSize);
        Page<TripResponse> trips = tripRepository.getTripByRecommended(pageable);
        return PaginationResponse.builder()
                .tripResponseList(trips.getContent())
                .currentPage(trips.getNumber()+1)
                .pageSize(trips.getTotalPages()).build();
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
            trip.setDateFrom(bookingRequest.getDateFrom());
            trip.setDateTo(bookingRequest.getDateTo());
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Тур успешно забронировано")
                .build();
    }

    @Override
    public SimpleResponse unBookingTrip(Long tripId) {
        User user = getAuthFromUser();

        Trip trip = tripRepository.findById(tripId).orElseThrow(() -> {
            log.info("Trip with id:" + tripId + " not found");
            return new NotFoundException("Тур с идентификатором: " + tripId + " не найдено");
        });
        if (trip.getReservoir().equals(user)) {
            if (trip.getBookingStatus().equals(BookingStatus.RESERVED)) {
                trip.setReservoir(null);
                trip.setBookingStatus(null);
                trip.setWishesToTrip(null);
                trip.setDateFrom(null);
                user.setPhoneNumber(null);
                tripRepository.save(trip);
                userRepository.save(user);
            } else {
                log.info("Wish is not reserved");
                throw new BadCredentialException("Пожелание не находится в забронированном состоянии");
            }
        } else {
            throw new BadCredentialException("Вы не можете отменить бронирование пожелание");
        }
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Тур успешно разбронирована")
                .build();
    }

    @Override
    public TripResponse getTripById(Long tripId) {
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

    @Override
    public List<TripResponse> getBookingTripsFromUser() {
        User user = getAuthFromUser();
        return tripJDBCTemplate.getBookingTrips(user.getEmail());
    }
}