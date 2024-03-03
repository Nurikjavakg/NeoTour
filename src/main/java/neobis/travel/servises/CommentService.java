package neobis.travel.servises;

import neobis.travel.dto.CommentRequest;
import neobis.travel.dto.SimpleResponse;

public interface CommentService {
    SimpleResponse commentToTrip(Long tripId, CommentRequest commentRequest);
}