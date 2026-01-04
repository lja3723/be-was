package handler.response;

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
    public void handleResponse() {
        //TODO: 핸들러 로직 구현
    }
}
