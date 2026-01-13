package app.exception;

/**
 * 잘못된 요청을 나타내는 예외 클래스
 * <p>이 예외는 클라이언트가 잘못된 형식의 요청을 보낼 때 던져짐</p>
 */
public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }
}