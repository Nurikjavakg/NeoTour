package neobis.travel.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import neobis.travel.dto.AuthenticationSignInResponse;
import neobis.travel.dto.AuthenticationSignUpResponse;
import neobis.travel.dto.SignInRequest;
import neobis.travel.dto.SignUpRequest;
import neobis.travel.servises.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "User Api")
@CrossOrigin(origins = "*", maxAge = 3600)
@PermitAll
public class UserApi {

    private final UserService userService;

    @PostMapping("/signIn")
    @Operation(summary = "Вход в свой аккаунт")
    public AuthenticationSignInResponse signIn(@RequestBody SignInRequest signInRequest) {
        return userService.signIn(signInRequest);
    }

    @PostMapping("/signUp")
    @Operation(summary = "Зарегистрироваться", description = "Регистрация  аккаунта")
    public AuthenticationSignUpResponse signUp(@RequestBody SignUpRequest signUpRequest) {
        return userService.signUp(signUpRequest);
    }
}
