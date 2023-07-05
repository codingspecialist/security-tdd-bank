package shop.mtcoding.tddbank._core.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import shop.mtcoding.tddbank.user.User;
import shop.mtcoding.tddbank.user.UserRepository;

import java.util.Optional;

// 컴퍼넌트 스캔시에 new CustomUserDetailsService() -> IoC 던져짐.
@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // login 호출
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("loadUserByUsername()");
        System.out.println("username : "+username);
        Optional<User> userOP = userRepository.findByUsername(username);

        if(userOP.isPresent()){
            return new CustomUserDetails(userOP.get());
        }else{
            return null;
        }

    }
}
