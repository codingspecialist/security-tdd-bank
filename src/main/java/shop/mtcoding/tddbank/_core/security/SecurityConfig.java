package shop.mtcoding.tddbank._core.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    public class CustomSecurityFilterManager extends AbstractHttpConfigurer<CustomSecurityFilterManager, HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder) throws Exception {
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);
            builder.addFilter(new CustomUsernamePasswordAuthenticationFilter(authenticationManager));
            super.configure(builder);
        }
    }

    @Bean // 컴퍼넌트 스캔시에 @Bean이 붙은 메서드가 있으면 실행시켜서 리턴되는 값을 IoC에 등록하는 깃발
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 1. CSRF 해제
        http.csrf().disable();

        // UsernamePasswordAuthenticationFilter (Form POST x-www-form-urlencoded) - 세션발동
        http.formLogin().disable();

        // HttpBasicAuthenticationFilter (헤더 username, password)
        http.httpBasic().disable();

        // 필터들을 갈아끼우는 내부 클래스
        http.apply(new CustomSecurityFilterManager());

        http.authorizeRequests(authorize ->  authorize.antMatchers("/").authenticated().anyRequest().permitAll());

        return http.build();
    }
}
