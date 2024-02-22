package neobis.travel.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import neobis.travel.dto.*;
import neobis.travel.servises.CommentService;
import neobis.travel.servises.TripService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trips")
@Tag(name = "Trip Api")
@CrossOrigin(origins = "*", maxAge = 3600)
@PermitAll
public class TripApi {

    private final TripService tripService;
    private final CommentService commentService;

    @PostMapping
    @Operation(summary = "Save trip", description = "You can save new trip")
    SimpleResponse saveTrip(@RequestBody TripRequest tripRequest) {
        return tripService.saveTrip(tripRequest);
    }

    @PutMapping("/{tripId}")
    @Operation(summary = "Feature trip", description = "You can feature trip")
    SimpleResponse feature(@PathVariable Long tripId) {
        return tripService.featured(tripId);
    }

    @PutMapping("/recommend/{tripId}")
    @Operation(summary = "recommend trip", description = "You can recommend trip")
    SimpleResponse recommend(@PathVariable Long tripId) {
        return tripService.recommended(tripId);
    }

    @GetMapping
    @Operation(summary = "Get populars trip", description = "You can get populars trip")
    List<TripResponse> getTripByPopular() {
        return tripService.getByPopular();
    }

    @GetMapping("/getByMostVisit")
    @Operation(summary = "Get most visits trip", description = "You can get most visits trip")
    List<TripResponse> getTripByMostVisit() {
        return tripService.getByMostVisit();
    }

    @GetMapping("/getByContinent")
    @Operation(summary = "Get trip by continent", description = "You can get trip by continent")
    List<TripResponse> getTripByContinent(String continent) {
        return tripService.getByContinent(continent);
    }

    @GetMapping("/getByFeatured")
    @Operation(summary = "Get trip by featured", description = "You can get trip by featured")
    List<TripResponse> getTripByFeatured() {
        return tripService.getTripByFeatured();
    }

    @GetMapping("/getByRecommended")
    @Operation(summary = "Get trip by recommended", description = "You can get trip by recommended")
    List<TripResponse> getTripByRecommend() {
        return tripService.getTripByRecommended();
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/{tripId}")
    @Operation(summary = "Бронирование тур", description = "Вы можете забронировать тур")
    public SimpleResponse bookingTrip(@PathVariable Long tripId,
                                      @RequestBody BookingRequest bookingRequest) {
        return tripService.bookingTrip(tripId, bookingRequest);
    }

    @PostMapping("/commentToTrip/{tripId}")
    @Operation(summary = "Comment trip", description = "You can save new trip")
    SimpleResponse commentToTrip(@PathVariable Long tripId, @RequestBody CommentRequest commentRequest) {
        return commentService.commentToTrip(tripId,commentRequest);
    }

    @GetMapping("/getTripWithComments/{tripId}")
    @Operation(summary = "Get trips comments", description = "You can get most visits trip")
    public TripResponse getCommentUser(@PathVariable Long tripId) {
        return tripService.getCommentUser(tripId);
    }
}