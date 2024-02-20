package neobis.travel.dto;

import lombok.Builder;

@Builder
public record SignInRequest (
    String email,
    String password
    ){}