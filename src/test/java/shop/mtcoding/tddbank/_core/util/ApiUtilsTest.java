package shop.mtcoding.tddbank._core.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import shop.mtcoding.tddbank.user.User;

public class ApiUtilsTest {

    @Test
    public void apiutil_error_test() throws JsonProcessingException {
        ApiUtils.ApiResult<?> data = ApiUtils.error("자원을 찾을 수 없습니다", HttpStatus.NOT_FOUND);
        ObjectMapper om = new ObjectMapper();
        String responseBody = om.writeValueAsString(data);
        System.out.println(responseBody);
    }

    @Test
    public void apiutil_test() throws JsonProcessingException {
        ApiUtils.ApiResult<String> data = ApiUtils.success("안녕");
        ObjectMapper om = new ObjectMapper();
        String responseBody = om.writeValueAsString(data);
        System.out.println(responseBody);
    }

    @Test
    public void apiutil_entity_test() throws JsonProcessingException {
        // given
        User ssar = User.builder()
                .username("ssar")
                .password("1234")
                .email("ssar@nate.com")
                .fullName("쌀")
                .status(true)
                .roles("ROLE_USER")
                .build();

        // when
        ApiUtils.ApiResult<User> data = ApiUtils.success(ssar);
        ObjectMapper om = new ObjectMapper();
        String responseBody = om.writeValueAsString(data);
        System.out.println(responseBody);

        // then
    }
}
