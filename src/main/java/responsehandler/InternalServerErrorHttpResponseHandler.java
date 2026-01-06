package responsehandler;

import webserver.http.ContentType;
import webserver.http.HttpStatus;
import webserver.http.HttpVersion;
import webserver.http.header.HttpResponseHeader;
import java.io.OutputStream;
import webserver.http.header.HttpRequestHeader;

/**
 * HTTP Status가 500, 서버 프로그램 내부에 오류가 있을 경우의 HTTP Response를 핸들링하는 ResponseHandler
 */
public class InternalServerErrorHttpResponseHandler extends HttpResponseHandler {

    @Override
    public byte[] getBody(HttpRequestHeader httpRequestHeader, OutputStream outputStream) {
        // TODO: 정적 리소스로 분리 후 정적 리소스 로드하기
        return "<html><body><h1>500 Internal Server Error</h1><p>서버 내부 오류가 발생했습니다.</p></body></html>"
            .getBytes();
    }

    @Override
    public HttpResponseHeader createResponseHeader(ContentType bodyContentType, byte[] body) {
        return HttpResponseHeader.builder()
            .version(HttpVersion.HTTP_1_1)
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .contentType(ContentType.TEXT_HTML)
            .body(body)
            .build();
    }
}
