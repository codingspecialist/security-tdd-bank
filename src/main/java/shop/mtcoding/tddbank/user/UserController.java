package shop.mtcoding.tddbank.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody UserRequest.JoinDTO joinDTO){ // json
        // 1. 유효성 검사

        // 2. 회원가입 (서비스요청) - 시큐리티는 패스워드 인코딩이 무조건 되어야 한다.
        joinDTO.setPassword(passwordEncoder.encode(joinDTO.getPassword()));
        userRepository.save(joinDTO.toEntity());

        // 3. 응답
        return ResponseEntity.ok().body("ok");
    }
}
