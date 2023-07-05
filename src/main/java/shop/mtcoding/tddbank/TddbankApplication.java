package shop.mtcoding.tddbank;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.mtcoding.tddbank._core.security.CustomUserDetails;
import shop.mtcoding.tddbank.user.User;
import shop.mtcoding.tddbank.user.UserRepository;

@RestController
@SpringBootApplication
public class TddbankApplication {


    @GetMapping("/tdd")
    public String tdd(@AuthenticationPrincipal CustomUserDetails userDetails){
        System.out.println(userDetails.getUser().getFullName());
        return userDetails.getUsername();
    }

    public static void main(String[] args) {
        SpringApplication.run(TddbankApplication.class, args);
    }

}
