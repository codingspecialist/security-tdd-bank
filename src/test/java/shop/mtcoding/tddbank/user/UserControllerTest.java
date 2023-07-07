package shop.mtcoding.tddbank.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK) // 가짜 스프링 환경
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void join_test() throws Exception {
        // given
        UserRequest.JoinDTO joinDTO = new UserRequest.JoinDTO();
        joinDTO.setUsername("love");
        joinDTO.setPassword("1234");
        joinDTO.setEmail("love@nate.com");
        joinDTO.setFullName("러브");
        ObjectMapper om = new ObjectMapper();
        String requestBody = om.writeValueAsString(joinDTO);
        System.out.println("테스트 : "+requestBody);

        // when
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.post("/join").content(requestBody).contentType(MediaType.APPLICATION_JSON));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        // then
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response.id").value(1));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response.username").value("love"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response.email").value("love@nate.com"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response.fullName").value("러브"));
    }
}
