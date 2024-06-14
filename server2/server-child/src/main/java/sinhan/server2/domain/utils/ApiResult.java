package sinhan.server2.domain.utils;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiResult<T> {
    private boolean success;
    private T data;
    private ApiError error;

    public ApiResult(boolean success, T data, ApiError error) {
        this.success = success;
        this.data = data;
        this.error = error;
    }

    public static <T> ApiResult<T> responseSuccess(T data){
        return new ApiResult<>(true,data,null);
    }

    public  static <T> ApiResult<T> responseError(String message){
        return new ApiResult<>(false,null, new ApiError(message, HttpStatus.CONFLICT));
    }

    public  static <T> ApiResult<T> responseError(String message,HttpStatus httpStatus){
        return new ApiResult<>(false,null, new ApiError(message, httpStatus));
    }
}

class ApiError{
    String message;
    HttpStatus httpStatus;

    public ApiError(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

}
