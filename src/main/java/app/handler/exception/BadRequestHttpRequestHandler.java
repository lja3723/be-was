package app.handler.exception;

import app.exception.BadRequestException;
import webserver.handler.exception.ExceptionHandler;
import webserver.http.ContentType;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpStatus;
import webserver.http.HttpVersion;
import webserver.http.header.HttpResponseHeader;

public class BadRequestHttpRequestHandler extends ExceptionHandler<BadRequestException> {

    @Override
    public HttpResponse handleException(HttpRequest httpRequest, BadRequestException e) {
        // TODO: 정적 리소스로 분리 후 정적 리소스 로드하기
        byte[] body = "<html><body><h1>400 Bad Request</h1><p>잘못된 요청을 시도하였습니다.</p></body></html>"
            .getBytes();

        return new HttpResponse(
            HttpResponseHeader.builder()
                .version(HttpVersion.HTTP_1_1)
                .status(HttpStatus.BAD_REQUEST)
                .contentType(ContentType.TEXT_HTML)
                .body(body)
                .build(),
            body);
    }
}