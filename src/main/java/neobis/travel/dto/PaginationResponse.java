package neobis.travel.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class PaginationResponse {
    List<TripResponse> tripResponseList;
    private int currentPage;
    private int pageSize;
}