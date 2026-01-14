package app.exception;

/**
 * 인증되지 않은 접근 시 발생하는 예외 클래스
 */
public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String message) {
        super(message);
    }
}
