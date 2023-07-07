package shop.mtcoding.tddbank._core.security;

import org.junit.jupiter.api.Test;
import shop.mtcoding.tddbank.user.User;

public class JwtTokenProviderTest {

    @Test
    public void create_test(){
        User ssar = User.builder()
                .id(1L)
                .username("ssar")
                .password("1234")
                .email("ssar@nate.com")
                .fullName("쌀")
                .status(true)
                .roles("ROLE_USER")
                .build();
        String jwt = JwtTokenProvider.create(ssar);
        System.out.println(jwt);
    }

//    @Test
//    public void verify_test(){
//        User ssar = User.builder()
//                .id(1L)
//                .username("ssar")
//                .password("1234")
//                .email("ssar@nate.com")
//                .fullName("쌀")
//                .status(true)
//                .roles("ROLE_USER")
//                .build();
//        String jwt = JwtTokenProvider.create(ssar);
//        JwtTokenProvider.verify(jwt);
//    }
}
