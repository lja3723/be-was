package app.handler;

import webserver.http.ContentType;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpStatus;
import webserver.http.HttpVersion;
import webserver.http.header.HttpResponseHeader;

/**
 * HTTP Status가 404, 리소스가 없는 경우의 HTTP Response를 핸들링하는 ResponseHandler
 */
public class ResourceNotFoundHttpRequestHandler extends HttpRequestHandler {

    @Override
    public HttpResponse handleResponse(HttpRequest httpRequest) {
        // TODO: 정적 리소스로 분리 후 정적 리소스 로드하기
        byte[] body = "<html><body><h1>404 Not Found</h1><p>요청하신 리소스를 찾을 수 없습니다.</p></body></html>".getBytes();

        return new HttpResponse(
            HttpResponseHeader.builder()
                .version(HttpVersion.HTTP_1_1)
                .status(HttpStatus.NOT_FOUND)
                .contentType(ContentType.TEXT_HTML)
                .body(body)
                .build(),
            body);
    }
}
