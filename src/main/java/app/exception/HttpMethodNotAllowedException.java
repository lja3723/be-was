package app.exception;

/**
 * HTTP 메서드가 허용되지 않음을 나타내는 예외 클래스
 * <p>이 예외는 클라이언트가 지원되지 않는 HTTP 메서드를 사용하여 요청을 보낼 때 던져짐</p>
 */
public class HttpMethodNotAllowedException extends RuntimeException {

    public HttpMethodNotAllowedException(String message) {
        super(message);
    }
}
