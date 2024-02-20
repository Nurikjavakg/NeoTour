package neobis.travel.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingRequest {
    private String phoneNumber;
    private String wishesToTrip;
    private Long userSum;
}
