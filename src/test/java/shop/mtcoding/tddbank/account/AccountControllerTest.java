package shop.mtcoding.tddbank.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import shop.mtcoding.tddbank.user.User;
import shop.mtcoding.tddbank.user.UserRepository;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK) // 가짜 스프링 환경
public class AccountControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp(){
        User ssar = User.builder()
                .username("ssar")
                .password(passwordEncoder.encode("1234"))
                .email("ssar@nate.com")
                .fullName("쌀")
                .status(true)
                .roles("USER")
                .build();
        userRepository.save(ssar);
    }

    // 통합테스트 @WithUserDetails, 단위테스트 @MyWithMockUser

    // 세션으로 테스트 할 수 있는 이유는 JWTAuthenticationFilter에서 토큰검증이 성공하면, 강제로 세션을 만들었기 때문에!!

    // 1. @WithUserDetails 먼저 실행된다. -> userDetailsService가 호출되서 DB에 ssar유저가 있는지 확인 -> setUp이 실행되고, saveAccount_test()
    // @WithUserDetails(value = "ssar")
    // 2. setUp이 실행되고 ->  @WithUserDetails 먼저 실행된다. -> userDetailsService가 호출되서 DB에 ssar유저가 있는지 확인 ->  saveAccount_test()
    @WithUserDetails(value = "ssar", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Test
    public void saveAccount_test() throws Exception {
        // given
        AccountRequest.SaveDTO saveDTO = new AccountRequest.SaveDTO();
        saveDTO.setNumber(1111);
        saveDTO.setPassword(1234);

        ObjectMapper om = new ObjectMapper();
        String requestBody = om.writeValueAsString(saveDTO);
        System.out.println("테스트 : "+requestBody);

        // when
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.post("/account").content(requestBody).contentType(MediaType.APPLICATION_JSON));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response.id").value(1));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response.number").value(1111));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response.balance").value(1000));
    }
}
