package neobis.travel.servises.servicesImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neobis.travel.dto.CommentRequest;
import neobis.travel.dto.SimpleResponse;
import neobis.travel.entity.Comment;
import neobis.travel.entity.Trip;
import neobis.travel.entity.User;
import neobis.travel.exceptions.NotFoundException;
import neobis.travel.repositories.CommentRepository;
import neobis.travel.repositories.TripRepository;
import neobis.travel.repositories.UserRepository;
import neobis.travel.servises.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Transactional
@Service
@Slf4j
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final TripRepository tripRepository;
    private final UserRepository userRepository;

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
    public SimpleResponse commentToTrip(Long tripId, CommentRequest commentRequest) {
        User user = getAuthFromUser();

        Trip trip = tripRepository.getTripByTripId(tripId).
                orElseThrow(()-> new NotFoundException("Trip with Id:"+tripId+" not found!"));
        Comment comment = new Comment();
        comment.setComment(commentRequest.getCommentText());
        user.getComment().add(comment);
        comment.setTrips(trip);
        trip.getComments().add(comment);
        comment.setUser(user);
        comment.setAnonymously(commentRequest.isAnonymously());
        commentRepository.save(comment);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Commented!")
                .build();
    }
}