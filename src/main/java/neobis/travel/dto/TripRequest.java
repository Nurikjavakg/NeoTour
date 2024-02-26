package neobis.travel.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;

@Builder
@Getter
@Setter
@NoArgsConstructor
public class TripRequest implements Serializable {
    private String name;
    private String tripImage;
    private String description;
    private String place;
    private String continent;;
    private boolean popular;
    private boolean mostVisited;
    private boolean featured;

    public TripRequest(String name, String tripImage, String description, String place,String continent, boolean popular, boolean mostVisited, boolean featured) {
        this.name = name;
        this.tripImage = tripImage;
        this.description = description;
        this.place = place;
        this.continent = continent;
        this.popular = popular;
        this.mostVisited = mostVisited;
        this.featured = featured;
    }

    public TripRequest(boolean featured) {
        this.featured = featured;
    }
}