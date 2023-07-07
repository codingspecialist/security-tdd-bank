package shop.mtcoding.tddbank.user;

import lombok.Getter;
import lombok.Setter;

public class UserResponse {

    @Getter @Setter
    public static class JoinDTO {
        private Long id;
        private String username;
        private String email;
        private String fullName;

        public JoinDTO(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.email = user.getEmail();
            this.fullName = user.getFullName();
        }
    }
}
