package app.requesthandler;

import webserver.http.ContentType;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpStatus;
import webserver.http.HttpVersion;
import webserver.http.header.HttpResponseHeader;

public class BadRequestHttpResponseHandler extends HttpResponseHandler {

    @Override
    public HttpResponse handleResponse(HttpRequest httpRequest) {
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