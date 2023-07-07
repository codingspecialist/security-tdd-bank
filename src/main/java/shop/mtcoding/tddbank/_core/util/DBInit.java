package shop.mtcoding.tddbank._core.util;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import shop.mtcoding.tddbank.user.User;
import shop.mtcoding.tddbank.user.UserRepository;

@RequiredArgsConstructor
@Component
public class DBInit {

    private final PasswordEncoder passwordEncoder;

    @Profile("dev")
    @Bean
    CommandLineRunner initDB(UserRepository userRepository){
        return args -> {
            User ssar = User.builder()
                    .username("ssar")
                    .password(passwordEncoder.encode("1234"))
                    .email("ssar@nate.com")
                    .fullName("쌀")
                    .status(true)
                    .roles("USER")
                    .build();
            userRepository.save(ssar);

            User admin = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("1234"))
                    .email("admin@nate.com")
                    .fullName("어드민")
                    .status(true)
                    .roles("ADMIN")
                    .build();
            userRepository.save(admin);
        };
    }
}
