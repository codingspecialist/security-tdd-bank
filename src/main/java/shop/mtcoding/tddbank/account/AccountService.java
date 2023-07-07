package shop.mtcoding.tddbank.account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.tddbank._core.erros.exception.Exception400;
import shop.mtcoding.tddbank._core.erros.exception.Exception404;
import shop.mtcoding.tddbank.user.User;
import shop.mtcoding.tddbank.user.UserRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    @Transactional
    public AccountResponse.SaveDTO 계좌등록(AccountRequest.SaveDTO saveInDTO, Long userId) {
        // 1. 회원 존재 여부
        User userPS = userRepository.findById(userId).orElseThrow(
                () -> new Exception404("유저를 찾을 수 없습니다"));

        // 2. 계좌 존재 여부
        Optional<Account> accountOP = accountRepository.findByNumber(saveInDTO.getNumber());
        if (accountOP.isPresent()) {
            throw new Exception400("number", "해당 계좌가 이미 존재합니다");
        }

        // 3. 계좌 등록
        Account accountPS = accountRepository.save(saveInDTO.toEntity(userPS));

        // 4. DTO 응답
        return new AccountResponse.SaveDTO(accountPS);
    }

    @Transactional(readOnly = true)
    public AccountResponse.ListDTO 유저계좌목록보기(Long userId) {
        // 1. 회원 존재 여부
        User userPS = userRepository.findById(userId).orElseThrow(
                () -> new Exception404("유저를 찾을 수 없습니다"));

        // 2. 유저 계좌목록 조회
        List<Account> accountListPS = accountRepository.findByUserId(userId);

        // 3. DTO 응답
        return new AccountResponse.ListDTO(userPS, accountListPS);
    }

    @Transactional(readOnly = true)
    public AccountResponse.DetailDTO 계좌상세보기(Integer number, Long userId) {
        // 1. 계좌 확인
        Account accountPS = accountRepository.findByNumber(number)
                .orElseThrow(
                        () -> new Exception404("계좌를 찾을 수 없습니다"));

        // 2. 계좌 소유주 확인
        accountPS.checkOwner(userId);
        // 3. DTO 응답
        return new AccountResponse.DetailDTO(accountPS);
    }
}
