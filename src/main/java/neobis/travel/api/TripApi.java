package neobis.travel.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import neobis.travel.dto.*;
import neobis.travel.servises.CommentService;
import neobis.travel.servises.TripService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trips")
@Tag(name = "Trip Api")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TripApi {

    private final TripService tripService;
    private final CommentService commentService;

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/saveTrip")
    @Operation(summary = "Save trip", description = "You can save new trip")
    SimpleResponse saveTrip(@RequestPart TripRequest tripRequest,@RequestPart("images") List<MultipartFile> images) {
        return tripService.saveTrip(tripRequest, images);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/{tripId}")
    @Operation(summary = "Feature trip", description = "You can feature trip")
    SimpleResponse feature(@PathVariable Long tripId) {
        return tripService.featured(tripId);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/recommend/{tripId}")
    @Operation(summary = "recommend trip", description = "You can recommend trip")
    SimpleResponse recommend(@PathVariable Long tripId) {
        return tripService.recommended(tripId);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/getByPopular")
    @Operation(summary = "Get populars trip", description = "You can get populars trip")
    List<TripResponse> getTripByPopular() {
        return tripService.getByPopular();
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/getByMostVisit")
    @Operation(summary = "Get most visits trip", description = "You can get most visits trip")
    List<TripResponse> getTripByMostVisit() {
        return tripService.getByMostVisit();
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/getAsiaTrips")
    @Operation(summary = "Get trips from Asia", description = "You can get trips from Asia")
    List<TripResponse> getAsiaTrips() {
        return tripService.getAsiaTrips();
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/getEuropeTrips")
    @Operation(summary = "Get trips from Europe", description = "You can get trips from Europe")
    List<TripResponse> getEuropeTrips() {
        return tripService.getEuropeTrips();
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/getByFeatured")
    @Operation(summary = "Get trip by featured", description = "You can get trip by featured")
    List<TripResponse> getTripByFeatured() {
        return tripService.getTripByFeatured();
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/getByRecommended")
    @Operation(summary = "Get trip by recommended", description = "You can get trip by recommended")
    PaginationResponse getTripByRecommend(@RequestParam int currentPage,
                                          @RequestParam int pageSize) {
        return tripService.getTripByRecommended(currentPage,pageSize);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/{tripId}")
    @Operation(summary = "Бронирование тур", description = "Вы можете забронировать тур")
    public SimpleResponse bookingTrip(@PathVariable Long tripId,
                                      @RequestBody BookingRequest bookingRequest) {
        return tripService.bookingTrip(tripId, bookingRequest);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/unBooking/{tripId}")
    @Operation(summary = "Разбронирование тур", description = "Вы можете разбронировать тур")
    public SimpleResponse unBookingTrip(@PathVariable Long tripId) {
        return tripService.unBookingTrip(tripId);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/commentToTrip/{tripId}")
    @Operation(summary = "Comment trip", description = "You can comment to trip")
    SimpleResponse commentToTrip(@PathVariable Long tripId, @RequestBody CommentRequest commentRequest) {
        return commentService.commentToTrip(tripId,commentRequest);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/getTripById/{tripId}")
    @Operation(summary = "Get trip by id", description = "You can get trip by id")
    public TripResponse getTripById(@PathVariable Long tripId) {
        return tripService.getTripById(tripId);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/getBookingTripsUser")
    @Operation(summary = "Get booking trips from user")
    public List<TripResponse> getBookingTripsUser() {
        return tripService.getBookingTripsFromUser();
    }
}