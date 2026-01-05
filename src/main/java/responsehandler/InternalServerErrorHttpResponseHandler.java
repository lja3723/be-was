package responsehandler;

import http.ContentType;
import http.header.HttpResponseHeader;
import java.io.OutputStream;
import http.header.HttpRequestHeader;

/**
 * HTTP Status가 500, 서버 프로그램 내부에 오류가 있을 경우의 HTTP Response를 핸들링하는 ResponseHandler
 */
//TODO: 핸들러 로직 구현
public class InternalServerErrorHttpResponseHandler extends HttpResponseHandler {

    @Override
    public byte[] getBody(HttpRequestHeader httpRequestHeader, OutputStream outputStream) {
        throw new UnsupportedOperationException("아직 구현되지 않음");
    }

    @Override
    public HttpResponseHeader createResponseHeader(ContentType bodyContentType, byte[] body) {
        throw new UnsupportedOperationException("아직 구현되지 않음");
    }
}
