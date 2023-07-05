package shop.mtcoding.tddbank._core.erros.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import shop.mtcoding.tddbank._core.util.ApiUtils;


// 권한 없음
@Getter
public class Exception403 extends RuntimeException {
    public Exception403(String message) {
        super(message);
    }

    public ApiUtils.ApiResult<?> body(){
        return ApiUtils.error(getMessage(), HttpStatus.FORBIDDEN);
    }

    public HttpStatus status(){
        return HttpStatus.FORBIDDEN;
    }
}