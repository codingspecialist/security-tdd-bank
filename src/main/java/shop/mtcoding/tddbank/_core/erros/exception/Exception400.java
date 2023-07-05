package shop.mtcoding.tddbank._core.erros.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import shop.mtcoding.tddbank._core.util.ApiUtils;


// 유효성 검사 실패, 잘못된 파라메터 요청
@Getter
public class Exception400 extends RuntimeException {

    private String key;
    private String value;

    public Exception400(String key, String value) {
        super(key+" : "+value);
        this.key = key;
        this.value = value;
    }

    public ApiUtils.ApiResult<?> body(){
        return ApiUtils.error(getMessage(), HttpStatus.BAD_REQUEST);
    }

    public HttpStatus status(){
        return HttpStatus.BAD_REQUEST;
    }
}