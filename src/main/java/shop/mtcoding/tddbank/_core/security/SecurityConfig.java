package shop.mtcoding.tddbank._core.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean // 컴퍼넌트 스캔시에 @Bean이 붙은 메서드가 있으면 실행시켜서 리턴되는 값을 IoC에 등록하는 깃발
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 1. CSRF 해제
        http.csrf().disable();

        http.formLogin()
                .loginProcessingUrl("/login"); // POST /login x-www-form-urlencoded

        http.authorizeRequests(authorize ->  authorize.antMatchers("/").authenticated().anyRequest().permitAll());

        return http.build();
    }
}
