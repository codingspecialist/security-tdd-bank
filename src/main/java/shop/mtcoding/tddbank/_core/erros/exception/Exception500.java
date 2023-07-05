package shop.mtcoding.tddbank._core.erros.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import shop.mtcoding.tddbank._core.util.ApiUtils;


@Getter
public class Exception500 extends RuntimeException {
    public Exception500(String message) {
        super(message);
    }


    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public HttpStatus status(){
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

}
