package webserver.handler.exception;

import app.exception.InternalServerErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.ContentType;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpStatus;
import webserver.http.HttpVersion;
import webserver.http.header.HttpResponseHeader;

/**
 * HTTP Status가 500, 서버 프로그램 내부에 오류가 있을 경우의 HTTP Response를 핸들링하는 ResponseHandler
 */
public class InternalServerErrorExceptionHandler extends ExceptionHandler<InternalServerErrorException> {

    private static final Logger log = LoggerFactory.getLogger(InternalServerErrorExceptionHandler.class);

    @Override
    public HttpResponse handleException(HttpRequest httpRequest, InternalServerErrorException e) {

        log.error("Handling Internal Server Error: {}, cause: {}", e.getMessage(), e.getCause(), e);

        // TODO: 정적 리소스로 분리 후 정적 리소스 로드하기
        byte[] body = ("<html><body><h1>500 Internal Server Error</h1><p>서버 내부 오류가 발생했습니다.</p><p>" +
            "An internal server error occurred. The exception could not be handled: \"" +
            e.getMessage() + "\"</p></body></html>")
                .getBytes();

        return new HttpResponse(
            HttpResponseHeader.builder()
                .version(HttpVersion.HTTP_1_1)
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(ContentType.TEXT_HTML)
                .body(body)
                .build(),
            body);
    }
}
