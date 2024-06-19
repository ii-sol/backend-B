package shinhan.server_common.notification.utils;

import shinhan.server_common.global.exception.CustomException;
import shinhan.server_common.global.exception.ErrorCode;

public class MessageHandler {
    public static String getMessage(Integer code, Object... args) {
        String message = MessageCode.getMessage(code);
        if (message == null) {
            throw new CustomException(ErrorCode.NOT_FOUND_MESSAGECODE);
        }
        return String.format(message, args);
    }

}
