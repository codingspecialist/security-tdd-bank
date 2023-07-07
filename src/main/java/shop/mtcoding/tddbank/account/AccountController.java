package shop.mtcoding.tddbank.account;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import shop.mtcoding.tddbank._core.erros.exception.Exception403;
import shop.mtcoding.tddbank._core.security.CustomUserDetails;
import shop.mtcoding.tddbank._core.util.ApiUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class AccountController {
    private final AccountService accountService;


    @PostMapping("/account")
    public ResponseEntity<?> saveAccount(@RequestBody @Valid AccountRequest.SaveDTO saveDTO, Errors errors, @AuthenticationPrincipal CustomUserDetails myUserDetails) {
        System.out.println("111111111111111111111111111111111111111111111111111111111111111111");
        AccountResponse.SaveDTO responseBody = accountService.계좌등록(saveDTO, myUserDetails.getUser().getId());
        System.out.println("2222222222222222222222222222222222222222222222222222222222222222222");
        return ResponseEntity.ok().body(ApiUtils.success(responseBody));
    }

    @GetMapping("/account/{number}")
    public ResponseEntity<?> findAccountDetail(@PathVariable Integer number, @AuthenticationPrincipal CustomUserDetails myUserDetails) {
        AccountResponse.DetailDTO responseBody = accountService.계좌상세보기(number, myUserDetails.getUser().getId());
        return ResponseEntity.ok().body(ApiUtils.success(responseBody));
    }

    @GetMapping("/account")
    public ResponseEntity<?> findUserAccountList(Long userId, @AuthenticationPrincipal CustomUserDetails myUserDetails) {
        if(userId.longValue() != myUserDetails.getUser().getId()){
            throw new Exception403("해당 계좌목록을 볼 수 있는 권한이 없습니다");
        }

        AccountResponse.ListDTO responseBody = accountService.유저계좌목록보기(userId);
        return ResponseEntity.ok().body(ApiUtils.success(responseBody));
    }
}
