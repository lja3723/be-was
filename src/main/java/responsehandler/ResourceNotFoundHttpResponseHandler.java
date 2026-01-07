package responsehandler;

import http.ContentType;
import http.header.HttpResponseHeader;
import java.io.OutputStream;
import http.header.HttpRequestHeader;

/**
 * HTTP Status가 404, 리소스가 없는 경우의 HTTP Response를 핸들링하는 ResponseHandler
 */
public class ResourceNotFoundHttpResponseHandler extends HttpResponseHandler {

    @Override
    public byte[] getBody(HttpRequestHeader httpRequestHeader, OutputStream outputStream) {
        // TODO: 정적 리소스로 분리 후 정적 리소스 로드하기
        return "<html><body><h1>404 Not Found</h1><p>요청하신 리소스를 찾을 수 없습니다.</p></body></html>"
            .getBytes();
    }

    @Override
    public HttpResponseHeader createResponseHeader(ContentType bodyContentType, byte[] body) {
        return HttpResponseHeader.builder()
            .version(http.HttpVersion.HTTP_1_1)
            .status(http.HttpStatus.NOT_FOUND)
            .contentType(ContentType.TEXT_HTML)
            .body(body)
            .build();
    }
}
