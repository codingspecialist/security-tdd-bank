package shop.mtcoding.tddbank._core.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import shop.mtcoding.tddbank.user.UserRequest;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public CustomUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
        this.authenticationManager = authenticationManager;
    }

    // /login POST 요청일 때 동작
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        ObjectMapper om = new ObjectMapper();

        try {
            UserRequest.LoginDTO loginDTO = om.readValue(request.getInputStream(), UserRequest.LoginDTO.class);
            UsernamePasswordAuthenticationToken token =
                    UsernamePasswordAuthenticationToken.unauthenticated(loginDTO.getUsername(),
                            loginDTO.getPassword());
            Authentication authentication = authenticationManager.authenticate(token); // UserDetailsService -> loadUserByUsername() 실행됨
            return authentication;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        // UserDetailsService가 UserDetails를 잘 리턴하면!!
        System.out.println("로그인 중에 성공함..........................................");
        super.successfulAuthentication(request, response, chain, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        // 인증서비스가 실행되다가 익셉션이 터지면
        System.out.println("로그인 중에 실패함.........................................");
        super.unsuccessfulAuthentication(request, response, failed);
    }
}
