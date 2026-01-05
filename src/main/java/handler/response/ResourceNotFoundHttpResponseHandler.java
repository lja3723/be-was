package handler.response;

import http.ContentType;
import http.header.HttpResponseHeader;
import java.io.OutputStream;
import http.header.HttpRequestHeader;

/**
 * HTTP Status가 404, 리소스가 없는 경우의 HTTP Response를 핸들링하는 ResponseHandler
 */
//TODO: 핸들러 로직 구현
public class ResourceNotFoundHttpResponseHandler extends HttpResponseHandler {

    @Override
    public byte[] getBody(HttpRequestHeader httpRequestHeader, OutputStream outputStream) {
        throw new UnsupportedOperationException("아직 구현되지 않음");
    }

    @Override
    public HttpResponseHeader createResponseHeader(ContentType bodyContentType, byte[] body) {
        throw new UnsupportedOperationException("아직 구현되지 않음");
    }
}
