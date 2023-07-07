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
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import shop.mtcoding.tddbank._core.erros.exception.Exception401;
import shop.mtcoding.tddbank._core.security.CustomUserDetails;
import shop.mtcoding.tddbank._core.security.JwtTokenProvider;
import shop.mtcoding.tddbank._core.util.ApiUtils;

import javax.validation.Valid;

// 요청 값 잘받기, 유효성검사 잘하기, 서비스 호출 잘하기, 응답 잘하기, 인증체크(시큐리티가해줌 X)
@Slf4j
@RequiredArgsConstructor
@RestController
public class UserController {


    private final UserService userService;
    private final AuthenticationManager authenticationManager;


    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody @Valid UserRequest.JoinDTO joinDTO, Errors errors){ // json
        UserResponse.JoinDTO responseDTO = userService.회원가입(joinDTO);
        return ResponseEntity.ok().body(ApiUtils.success(responseDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserRequest.LoginDTO loginDTO, Errors errors){
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
