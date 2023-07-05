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

    @Test
    public void verify_test(){
        JwtTokenProvider.verify("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzc2FyQG5hdGUuY2wicm9sZSI6IlJPTEVfVVNFUiIsImlkIjoxLCJleHAiOjE2ODg3MzQxMTB9.v--dQZYzfVY8wEylDJdMqsjNSrUWHzhiy9x1g0DQJj2_0KXiXyScFyf-cfu0wcLwHnRs7SvVd1BV31Oog82bAA");

    }
}
