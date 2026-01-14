package app.handler.exception;

import app.exception.ForbiddenException;
import webserver.handler.exception.ExceptionHandler;
import webserver.http.ContentType;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpStatus;
import webserver.http.HttpVersion;
import webserver.http.header.HttpResponseHeader;

/**
 * HTTP Status가 403, 접근이 금지된 경우의 HTTP Response를 핸들링하는 ResponseHandler
 */
public class ForbiddenExceptionHandler extends ExceptionHandler<ForbiddenException> {

    @Override
    public HttpResponse handleException(HttpRequest request, ForbiddenException exception) {
        // TODO: 정적 리소스로 분리 후 정적 리소스 로드하기
        byte[] body = "<html><body><h1>403 Forbidden</h1><p>요청하신 리소스에 접근할 수 있는 권한이 없습니다.</p></body></html>".getBytes();

        return new HttpResponse(
            HttpResponseHeader.builder()
                .version(HttpVersion.HTTP_1_1)
                .status(HttpStatus.FORBIDDEN)
                .contentType(ContentType.TEXT_HTML)
                .body(body)
                .build(),
            body);
    }
}
