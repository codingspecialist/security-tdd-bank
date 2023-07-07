package shop.mtcoding.tddbank.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import shop.mtcoding.tddbank._core.erros.exception.Exception401;
import shop.mtcoding.tddbank._core.security.CustomUserDetails;
import shop.mtcoding.tddbank._core.security.JwtTokenProvider;
import shop.mtcoding.tddbank._core.util.ApiUtils;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody UserRequest.JoinDTO joinDTO){ // json
        // 1. 유효성 검사

        // 2. 회원가입 (서비스요청) - 시큐리티는 패스워드 인코딩이 무조건 되어야 한다.
        joinDTO.setPassword(passwordEncoder.encode(joinDTO.getPassword()));
        User userPS = userRepository.save(joinDTO.toEntity());
        UserResponse.JoinDTO responseDTO = new UserResponse.JoinDTO(userPS);

        // 3. 응답
        return ResponseEntity.ok().body(ApiUtils.success(responseDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequest.LoginDTO loginDTO){

        try {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                    = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());
            Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            CustomUserDetails myUserDetails = (CustomUserDetails) authentication.getPrincipal();
            System.out.println("myUserDetails : "+myUserDetails.getUser().getUsername());

            // JWT 토큰 만들기
            String jwt = JwtTokenProvider.create(myUserDetails.getUser());

            return ResponseEntity.ok().header("Authorization", jwt).body(ApiUtils.success(null));

        }catch (Exception e){
            throw new Exception401("인증되지 않았습니다");
        }

    }
}
