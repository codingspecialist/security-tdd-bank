package shop.mtcoding.tddbank.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// 비지니스 로직 관리, 트랜잭션 관리, DTO를 만들어요.
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponse.JoinDTO 회원가입(UserRequest.JoinDTO joinDTO){
        joinDTO.setPassword(passwordEncoder.encode(joinDTO.getPassword()));
        User userPS = userRepository.save(joinDTO.toEntity());
        return new UserResponse.JoinDTO(userPS);
    } // commit, 영속화된 엔티티가 변경되면 flush() 더티체킹
}
