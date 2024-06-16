package sinhan.server1.domain.utils.exception;

public class KoreaInvestApiException extends RuntimeException{
    private String message;

    public KoreaInvestApiException(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "PasswordNotValidException{" +
            "message='" + message + '\'' +
            '}';
    }
}
