package neobis.travel.dto;

import lombok.Getter;
import lombok.Setter;
import neobis.travel.enums.Role;

@Getter
@Setter
public class AuthenticationSignUpResponse {
    private Long userId;
    private String fullName;
    private String token;
    private String email;
    private Role role;

    public AuthenticationSignUpResponse(Long userId, String fullName, String token, String email, Role role) {
        this.userId = userId;
        this.fullName = fullName;
        this.token = token;
        this.email = email;
        this.role = role;
    }
}
