package neobis.travel.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TripResponse {
    private Long tripId;
    private String name;
    private String tripImage;;
    private String description;
    private String place;
    private boolean continent;
    private boolean popular;
    private boolean mostVisited;
    private List<CommentResponse> commentResponse;



    public TripResponse(Long tipId, String name, String tripImage) {
        this.tripId = tipId;
        this.name = name;
        this.tripImage = tripImage;
    }

    public TripResponse(Long tipId, String name, String tripImage, String description, String place) {
        this.tripId = tipId;
        this.name = name;
        this.tripImage = tripImage;
        this.description = description;
        this.place = place;
    }



    public TripResponse(Long tripId, String name, String tripImage, String description, String place, List<CommentResponse> commentResponseList) {
        this.tripId = tripId;
        this.name = name;
        this.tripImage = tripImage;
        this.description = description;
        this.place = place;
        this.commentResponse = commentResponseList;
    }

}