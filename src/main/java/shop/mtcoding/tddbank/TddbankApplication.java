package shop.mtcoding.tddbank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class TddbankApplication {

    @GetMapping("/tdd")
    public String tdd(@AuthenticationPrincipal UserDetails userDetails){
        return userDetails.getUsername();
    }

    public static void main(String[] args) {
        SpringApplication.run(TddbankApplication.class, args);
    }

}
