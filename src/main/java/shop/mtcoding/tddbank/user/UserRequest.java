package shop.mtcoding.tddbank.user;

import lombok.Getter;
import lombok.Setter;
import lombok.Singular;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserRequest {

    @Getter @Setter
    public static class LoginDTO {
        @Pattern(regexp = "^[a-zA-Z0-9]{2,20}$", message = "영문/숫자 2~20자 이내로 작성해주세요")
        @NotEmpty
        private String username;
        @NotEmpty
        @Size(min = 4, max = 20)
        private String password;
    }

    @Getter @Setter
    public static class JoinDTO {
        @NotEmpty
        @Pattern(regexp = "^[a-zA-Z0-9]{2,20}$", message = "영문/숫자 2~20자 이내로 작성해주세요")
        private String username;

        @NotEmpty
        @Size(min = 4, max = 20)
        private String password;

        @NotEmpty
        @Pattern(regexp = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", message = "이메일 형식으로 작성해주세요")
        private String email;

        @NotEmpty
        @Pattern(regexp = "^[a-zA-Z가-힣]{1,20}$", message = "한글/영문 1~20자 이내로 작성해주세요")
        private String fullName;

        public User toEntity(){
            return User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .fullName(fullName)
                    .status(true)
                    .roles("ROLE_USER")
                    .build();
        }
    }
}
