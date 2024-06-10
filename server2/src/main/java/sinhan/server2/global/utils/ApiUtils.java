package sinhan.server2.global.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public class ApiUtils {

    public static <T> ApiResult<T> success(T data) {
        return new ApiResult(true, data, null);
    }

    public static <T> ApiResult<T> error(T message, HttpStatus httpStatus) {
        return new ApiResult(false, null, new ApiError(message, httpStatus));
    }

    @Getter
    @AllArgsConstructor
    public static class ApiResult<T> {

        private boolean success;
        private T response;
        private ApiError error;
    }

    @Getter
    @AllArgsConstructor
    static class ApiError<T> {

        private T message;
        private HttpStatus httpStatus;
    }
}