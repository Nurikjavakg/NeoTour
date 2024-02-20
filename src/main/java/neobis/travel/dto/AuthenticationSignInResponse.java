package neobis.travel.dto;

import lombok.Builder;
import neobis.travel.enums.Role;

@Builder
public record AuthenticationSignInResponse (
    Long id,
    String fullName,
    String token,
    String email,
    Role role
){
}