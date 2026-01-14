package app.exception;

/**
 * 권한이 없는 접근 시 발생하는 예외 클래스
 */
public class ForbiddenException extends RuntimeException {

    public ForbiddenException(String message) {
        super(message);
    }
}
