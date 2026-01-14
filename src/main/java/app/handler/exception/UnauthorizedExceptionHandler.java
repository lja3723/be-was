package app.handler.exception;

import app.exception.UnauthorizedException;
import webserver.handler.exception.ExceptionHandler;
import webserver.http.ContentType;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpStatus;
import webserver.http.HttpVersion;
import webserver.http.field.HttpField;
import webserver.http.field.HttpFieldKey;
import webserver.http.header.HttpResponseHeader;

/**
 * HTTP Status가 401, 인증되지 않은 경우의 HTTP Response를 핸들링하는 ResponseHandler
 */
public class UnauthorizedExceptionHandler extends ExceptionHandler<UnauthorizedException> {

    @Override
    public HttpResponse handleException(HttpRequest request, UnauthorizedException exception) {
        // TODO: 정적 리소스로 분리 후 정적 리소스 로드하기
        byte[] body = "<html><body><h1>401 Unauthorized</h1><p>요청하신 리소스에 접근할 수 있는 권한이 없습니다.</p></body></html>".getBytes();

        return new HttpResponse(
            HttpResponseHeader.builder()
                .version(HttpVersion.HTTP_1_1)
                .status(HttpStatus.UNAUTHORIZED)
                .contentType(ContentType.TEXT_HTML)
                .field(HttpField.builder()
                    .key(HttpFieldKey.WWW_AUTHENTICATE)
                    .value("Cookie realm=\"be-was-codestagram\", name=\"sid\"")
                    .build())
                .body(body)
                .build(),
            body);
    }
}
