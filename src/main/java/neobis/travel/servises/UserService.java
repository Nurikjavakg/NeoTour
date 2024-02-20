package neobis.travel.servises;

import neobis.travel.dto.*;

public interface UserService {
    AuthenticationSignUpResponse signUp(SignUpRequest authenticationSignUpRequest);
    AuthenticationSignInResponse signIn(SignInRequest signInRequest);
}
