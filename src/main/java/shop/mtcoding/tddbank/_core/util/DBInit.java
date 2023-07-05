package shop.mtcoding.tddbank._core.util;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import shop.mtcoding.tddbank.user.User;
import shop.mtcoding.tddbank.user.UserRepository;

@RequiredArgsConstructor
@Component
public class DBInit {

    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner initDB(UserRepository userRepository){
        return args -> {
            User ssar = User.builder()
                    .username("ssar")
                    .password(passwordEncoder.encode("1234"))
                    .email("ssar@nate.com")
                    .fullName("쌀")
                    .status(true)
                    .roles("ROLE_USER")
                    .build();
            userRepository.save(ssar);
        };
    }
}
