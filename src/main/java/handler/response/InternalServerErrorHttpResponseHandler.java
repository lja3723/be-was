package handler.response;

import http.ContentType;
import http.header.HttpResponseHeader;
import java.io.OutputStream;
import http.header.HttpRequestHeader;

/**
 * HTTP Status가 500, 서버 프로그램 내부에 오류가 있을 경우의 HTTP Response를 핸들링하는 ResponseHandler
 */
public class InternalServerErrorHttpResponseHandler extends HttpResponseHandler {

    public InternalServerErrorHttpResponseHandler(HttpRequestHeader httpRequestHeader, OutputStream outputStream) {
        super(httpRequestHeader, outputStream);
    }

    @Override
    public byte[] getBody(HttpRequestHeader httpRequestHeader) {
        //TODO: 핸들러 로직 구현
        throw new UnsupportedOperationException("아직 구현되지 않음");
    }

    @Override
    public HttpResponseHeader createResponseHeader(ContentType bodyContentType, byte[] body) {
        //TODO: 핸들러 로직 구현
        throw new UnsupportedOperationException("아직 구현되지 않음");
    }
}
