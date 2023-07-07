package shop.mtcoding.tddbank.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import shop.mtcoding.tddbank._core.MyWithMockUser;
import shop.mtcoding.tddbank._core.erros.MyExceptionHandler;
import shop.mtcoding.tddbank._core.erros.MyValidationHandler;
import shop.mtcoding.tddbank._core.security.SecurityConfig;
import shop.mtcoding.tddbank.user.User;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

// 서비스 X, 레파지토리 X
@ActiveProfiles("test")
@EnableAspectJAutoProxy // AOP 활성화 (벨리데이션 체크)
@Import({
        SecurityConfig.class,
        MyValidationHandler.class,
        MyExceptionHandler.class
})
@WebMvcTest(controllers = {AccountController.class})
public class AccountControllerUnitTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AccountService accountService;

    @MyWithMockUser(id = 1L, username = "ssar")
    @Test
    public void saveAccount_test() throws Exception {
        // given
        AccountRequest.SaveDTO saveDTO = new AccountRequest.SaveDTO();
        saveDTO.setNumber(1111);
        saveDTO.setPassword(1234);

        ObjectMapper om = new ObjectMapper();
        String requestBody = om.writeValueAsString(saveDTO);
        System.out.println("테스트 requestBody : "+requestBody);

        // stub
        User user = User.builder()
                .id(1L)
                .username("ssar")
                .password("1234")
                .email("ssar@nate.com")
                .fullName("쌀")
                .status(true)
                .roles("USER")
                .build();

        Account account = Account.builder()
                .id(1L)
                .user(user)
                .number(1111)
                .password(1234)
                .balance(1000L)
                .status(true)
                .build();

        AccountResponse.SaveDTO responseDTO = new AccountResponse.SaveDTO(account);
        String check = om.writeValueAsString(responseDTO);
        System.out.println("테스트 check : "+check);

        Mockito.when(accountService.계좌등록(any(), any())).thenReturn(responseDTO);


        // when
        ResultActions resultActions = mvc.perform(post("/account").content(requestBody).contentType(MediaType.APPLICATION_JSON));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 responseBody : "+responseBody);

        // then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response.id").value(1));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response.number").value(1111));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response.balance").value(1000));
    }

}
