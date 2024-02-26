package neobis.travel.servises.servicesImpl;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neobis.travel.config.JwtService;
import neobis.travel.dto.AuthenticationSignInResponse;
import neobis.travel.dto.AuthenticationSignUpResponse;
import neobis.travel.dto.SignInRequest;
import neobis.travel.dto.SignUpRequest;
import neobis.travel.entity.User;
import neobis.travel.enums.Role;
import neobis.travel.exceptions.AlreadyExistException;
import neobis.travel.exceptions.BadCredentialException;
import neobis.travel.exceptions.NotFoundException;
import neobis.travel.repositories.UserRepository;
import neobis.travel.servises.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    @Override
    public AuthenticationSignUpResponse signUp(SignUpRequest request) {
        if (userRepository.existsUserByEmail(request.getEmail())) {
            throw new AlreadyExistException("Пользователь с адресом электронной почты:"
                    + request.getEmail() + " уже существует");
        }
        User user = new User();
        user.setFirstName(request.getFirsName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);

        log.info("Пользователь успешно сохранен с идентификатором:" + user.getEmail());
        String token = jwtService.generateToken(user);
        return new AuthenticationSignUpResponse(
                user.getUserId(),
                user.getFirstName() + " " + user.getLastName(),
                token,
                user.getEmail(),
                user.getRole()
        );
    }

    @Override
    public AuthenticationSignInResponse signIn(SignInRequest signInRequest) {
        User user = userRepository.getUserByEmail(signInRequest.email()).orElseThrow(() ->{
                log.info("User with email:"+signInRequest.email()+" not found!");
            return new NotFoundException("Пользователь с адресом электронной почты:" + signInRequest.email() + " не найден!");
        });

        if (!passwordEncoder.matches(signInRequest.password(), user.getPassword())) {
            log.info("Недействительный пароль");
            throw new BadCredentialException("Недействительный пароль");
        }
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            signInRequest.email(),
                            signInRequest.password()));
            String token = jwtService.generateToken(user);
            return AuthenticationSignInResponse.builder()
                    .id(user.getUserId())
                    .fullName(user.getFirstName() + " " + user.getLastName())
                    .token(token)
                    .email(user.getEmail())
                    .role(user.getRole())
                    .build();
        }

    @PostConstruct
    public void initSaveAdmin() {
        User user = User.builder()
                .firstName("Ulan")
                .lastName("Kubanychbekov")
                .email("admin@gmail.com")
                .phoneNumber("+996705453456")
                .password(passwordEncoder.encode("admin123"))
                .role(Role.ADMIN)
                .build();
            log.info("Администратор успешно сохранен с идентификатором:" + user.getUserId());
        }
    }