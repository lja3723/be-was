package app.exception;

import webserver.http.header.HttpRequestHeader;
import java.io.OutputStream;

/**
 * 서버 내부 오류를 나타내는 예외 클래스
 * <p>이 예외는 서버 프로그램에서 던져지는 예외가 처리되지 않으면 최종적으로 던져져야 함</p>
 */
public class InternalServerErrorException extends RuntimeException {

    private final OutputStream outputStream;
    private final HttpRequestHeader httpRequestHeader;

    public InternalServerErrorException(OutputStream outputStream, HttpRequestHeader httpRequestHeader, String message, Throwable cause) {
        super(message, cause);
        this.outputStream = outputStream;
        this.httpRequestHeader = httpRequestHeader;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public HttpRequestHeader getHttpRequestHeader() {
        return httpRequestHeader;
    }
}
