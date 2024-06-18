package sinhan.server2.notification.utils;

import sinhan.server2.global.exception.CustomException;
import sinhan.server2.global.exception.ErrorCode;

public class MessageHandler {
    public static String getMessage(Integer code, Object... args) {
        String message = MessageCode.getMessage(code);
        if (message == null) {
            throw new CustomException(ErrorCode.NOT_FOUND_MESSAGECODE);
        }
        return String.format(message, args);
    }

}
