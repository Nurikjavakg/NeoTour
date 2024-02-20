package neobis.travel.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
public class SignUpRequest {
    private String firsName;
    private String lastName;
    private String email;
    private String password;
    private String userImage;

    public SignUpRequest(String firsName, String lastName, String email, String password, String userImage) {
        this.firsName = firsName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.userImage = userImage;
    }
}
