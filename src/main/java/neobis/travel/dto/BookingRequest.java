package neobis.travel.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BookingRequest {
    private String phoneNumber;
    private String wishesToTrip;
    private Long userSum;
    private LocalDate dateFrom;
    private LocalDate dateTo;
}
