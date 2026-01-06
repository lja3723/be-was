package app.exception;

import webserver.http.HttpRequest;

/**
 * 요청한 자원을 찾을 수 없음을 나타내는 예외 클래스
 * <p>이 예외는 클라이언트가 요청한 리소스가 서버에 존재하지 않을 때 던져짐</p>
 */
public class ResourceNotFoundException extends RuntimeException {

    private final HttpRequest httpRequest;

    public ResourceNotFoundException(HttpRequest httpRequest, String message) {
        super(message);
        this.httpRequest = httpRequest;
    }

    public HttpRequest getHttpRequest() {
        return httpRequest;
    }
}
